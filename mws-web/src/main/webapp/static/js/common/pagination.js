/**
 * Created by whan on 10/9/15.
 */
(function(root, factory) {
    "use strict";

    root.Pagination = factory(root.jQuery);
}(this, function init($) {
    "use strict";

    var defaults = {
        form: "#pagination-form",
        page: 1,
        pageSize: 15,
        container: "#pagination-body",
        checkboxClass: ".checkbox-item",
        globalCheckBox: ".checkbox-global",
        editBtn: {
            className: ".btn-edit-action",
            url: "",
            method: "GET",
            action: function() {}
        },
        deleteBtn: {
            className: ".btn-delete-action",
            url: "",
            method: "POST",
            callback: function(){}
        },
        searchBtn: {
            className: "btn-search"
        }
    };

    var templates = {
        //backdrop: "<div class=\"modal-backdrop fade in\"><div class=\"ball-zig-zag loading\"><div></div><div></div></div></div>"
        backdrop: "<div class=\"pagination-backdrop\"><div class=\"ball-zig-zag loading\"><div></div><div></div></div></div>"
    };

    var container, form, params, page, checkboxItems;

    /**
     * 查询选中的复选框值
     *
     * @param items
     */
    function getIds (items) {
        var ids = [];
        if (typeof items != "undefined") {
            items.each(function() {
                if (this.checked) {
                    ids.push(this.value);
                }
            });
        }
        return ids;
    }

    var exports = {

        /** 当前页码 */
        currentPage: 0,

        /**
         * 初始化分页
         *
         * @param form 表单
         * @param container 容器
         */
        init: function(options) {

            // 解析参数
            params = $.extend(true, defaults, options);

            // form表单初始化
            form = $(params.form);
            if (typeof form == "undefined") {
                throw new Error("Missing necessary parameter: form!");
            }

            // 监听表单的键盘事件
            form.on("keydown", function(e) {
                // Enter键
                if (e.keyCode == 13) {
                    e.preventDefault();

                    exports.reload();
                }
            });

            page = form.find("[name=page]");
            if (typeof page[0] == "undefined") {
                form.append('<input type="hidden" name="page" value="'+ params.page +'">');
                page = form.find("[name=page]");
            }
            form.append('<input type="hidden" name="pageSize" value="'+ params.pageSize +'">');

            // 异步页面容器初始化
            container = $(params.container);
            if (typeof container == "undefined") {
                throw new Error("Missing necessary parameter: container!");
            }
            container.css({"position": "relative", minHeight: "180px"});

            $(params.editBtn.className).on("click", function(event) {
                event.preventDefault();

                // 查询选中的复选框值
                var ids = getIds(checkboxItems);

                // 判断是否选中了要编辑的内容
                if (ids.length < 1) {
                    Dialog.info("请先选中您要编辑的内容");
                    return;
                }

                // 判断要编辑的记录是否超过限制
                if (ids.length > 1) {
                    Dialog.warning("警告！一次只能编辑一条记录");
                    return;
                }

                // 判断地址是否为空
                if (params.editBtn.url != "") {
                    // 跳转到编辑页面
                    var href = params.editBtn.url.replace("{id}", ids[0]);
                    if (href.indexOf("?") > 0) {
                        href += ("&page=" + page.val());
                    } else {
                        href += ("?page=" + page.val());
                    }
                    location.href =  href;
                } else {
                    // 编辑事件
                    if (typeof params.editBtn.action == "function") {
                        params.editBtn.action(ids[0]);
                    }
                }
            });


            $(params.deleteBtn.className).on("click", function(event) {
                event.preventDefault();

                // 查询选中的复选框值
                var ids = getIds(checkboxItems);

                // 判断是否选中了要编辑的内容
                if (ids.length < 1) {
                    Dialog.info("请先选中您要删除的内容");
                    return;
                }

                Dialog.confirm("删除提示", "确认要删除选择的内容吗？", function(result) {
                    if (result == true) {
                        $.ajax({
                            url: params.deleteBtn.url,
                            type: params.deleteBtn.method,
                            dataType: "JSON",
                            data: {
                                id: ids
                            },
                            success: function(data) {
                                if (data.code == 0) {
                                    exports.load(page.val());
                                    Dialog.success(data.msg);
                                    if (typeof params.deleteBtn.callback() == "function") {
                                        params.deleteBtn.callback(data);
                                    }
                                }
                            }
                        });
                    }
                });

            });

            exports.load(page.val());

            $(".btn-search").on("click", function() {
                exports.reload();
            })
        },

        /**
         * 获取选中的ids
         */
        getCheckedIds: function() {
            return getIds(checkboxItems);
        },

        /**
         * 绑定checkbox行为
         */
        bindCheckboxAction: function() {
            // 获取所有复选框
            checkboxItems = $(params.checkboxClass);

            $(params.globalCheckBox).on("change", function() {
                // 获取当前值
                var value = $(this).prop("checked");
                // 设置所有复选框的值
                checkboxItems.prop("checked", value);
            });
        },

        /**
         * 加载分页
         *
         * @param pagth 分页
         */
        load: function(pagth) {
            if (typeof form == "undefined") {
                throw new Error("Please initialize first!");
            }
            if (typeof pagth == "undefined") {
                pagth = 1;
            }

            if (pagth < 1) {
                Dialog.warning("客官，前面没有了");
                return;
            }

            page.val(pagth);

            // 设置当前页码
            this.currentPage = pagth;

            $.ajax({
                url: form.attr('action'),
                type: "POST",
                data: form.serialize(),
                beforeSend: function() {
                    container.append(templates.backdrop);
                },
                success: function(data) {
                    container.empty().append(data);
                    exports.bindCheckboxAction();
                }
            })
        },

        /**
         * 刷新当前页
         */
        reload: function() {
            this.load(page.val());
        }

    };

    return exports;
}));

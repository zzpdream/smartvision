$(function () {

    var list = {

        /** 初始化 */
        init: function () {
            this.initPagination();
            this.bindAddEvent();
        },

        /** 初始化分页 */
        initPagination: function () {
            var self = this;
            Pagination.init({
                pageSize: 10,
                editBtn: {
                    url: ctx + "/terminal/edit/{id}"
                },
                deleteBtn: {
                    url: ctx + "/terminal/delete",
                    method: "POST",
                    callback: function (data) {
                        // 回调函数
                    }
                }
            });
        },

        /** 绑定添加用户事件 */
        bindAddEvent: function () {
            $(".btn-add").on("click", function () {
                location.href = ctx + "/terminal/add";
            });
        }
    };
    list.init();
});

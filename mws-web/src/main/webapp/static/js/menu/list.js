$(function() {

    var list = {

        /** 初始化 */
        init: function() {
            this.initPagination();
            this.bindPermissionAction();
        },

        /** 初始化分页 */
        initPagination: function() {
            Pagination.init({
                pageSize: 12,   // 每页显示12条记录
                editBtn: {
                    url: ctx + "/menu/{id}"
                },
                deleteBtn: {
                    url: ctx + "/menu/delete",
                    callback: function(data) {
                        // 回调函数

                    }
                }
            });
        },

        /** 绑定查看权限点功能 */
        bindPermissionAction: function() {
            $(".btn-permission-action").on("click", function() {
                var ids = Pagination.getCheckedIds();

                if (ids.length <= 0) {
                    Dialog.warning("一次只能查看一条记录的权限点");
                    return;
                }

                window.location.href = ctx + "/menu/permissions?menu_id=" + ids[0] + "&menu_page=" + Pagination.currentPage + "&pid=" + $("input[name=pid]").val();
            });
        }

    };

    list.init();
});

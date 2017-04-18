/**
 * Created by whan on 11/5/15.
 */
(function(root, factory) {
    "use strict";

    root.Editor = factory(root.jQuery)
}(this, function init($) {
    "use strict";

    var Editor = function Editor(textarea) {
        return this.init(textarea);
    };

    Editor.prototype = {

        /**
         * 初始化编辑器
         *
         * @param textarea
         * @returns {*}
         */
        init: function(textarea) {
            console.info(textarea);

            if (textarea == "") {
                throw new Error("Missing a necessary parameter!");
            }

            return new Simditor({
                textarea: $(textarea),
                toolbar: ['bold', 'italic', 'underline', 'strikethrough', '|', 'ol', 'ul', 'blockquote', 'code', '|', 'link', 'image', '|', 'indent', 'outdent', '|', 'html', 'markdown'],
                defaultImage: window.ctx + "/static/third/simditor/images/image.png",
                upload: {
                    url: window.ctx + "/attachment/editorImage",
                    fileKey: "image",
                    connectionCount: 3,
                    leaveConfirm: "图片上传中，确定要关闭本页吗？"
                },
                pasteImage: true
            });
        }
    };


    return Editor;
}));
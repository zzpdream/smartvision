/**
 * Created by whan on 12/4/2014.
 */

(function(root, factory) {
    "use strict";

    root.Dialog = factory(root.jQuery);
}(this, function init($) {
    "use strict";

    /*HTML templates*/
    var templates = {
        dialog: {
            frame:
            "<div class='modal fade' tabindex='-1' role='dialog'>" +
            "<div class='modal-dialog'>" +
            "<div class='modal-content'>" +
            "<div class='modal-header'><h4 class='modal-title'></h4></div>" +
            "<div class='modal-body'></div>" +
            "<div class='modal-footer'></div>" +
            "</div>" +
            "</div>" +
            "</div>",
            closeButton: "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button>"
        },
        loading: "<div class=\"loading-message\"><div class=\"ball-triangle-path\"><div></div><div></div><div></div></div></div><div class=\"modal-backdrop fade in dialog-backdrop\"></div>"
    };

    /* Language */
    var locales = {
        zh_CN: {
            OK: 'OK',
            CLOSE: '关闭',
            CANCEL: '取消',
            CONFIRM: '确认'
        },
        en: {
            OK: 'OK',
            CLOSE: 'Close',
            CANCEL: 'Cancel',
            CONFIRM: 'Confirm'
        }
    };

    /* Default settings */
    var defaults = {
        locale: "zh_CN",
        backdrop: "static",
        container: "body",
        expire: 3000
    };

    var exports = {

        /*警告框*/
        alert: function() {
            var options = this.mergeDialogOptions("alert", ["ok"], ["title", "message", "callback"], arguments);

            options.buttons.ok.callback = options.onEscape = function() {
                if ($.isFunction(options.callback)) {
                    return options.callback();
                }
                return true;
            };

            options.buttons.ok.className = "btn-primary";

            if (!options.title) {
                options.title = "警告";
            }

            return this.dialog(options);
        },

        /*确认框*/
        confirm: function() {
            var options = this.mergeDialogOptions("confirm", ["cancel", "confirm"], ["title", "message", "callback"], arguments);

            if (!$.isFunction(options.callback)) {
                throw new Error("Confirm dialog requires a callback!");
            }

            options.buttons.cancel.callback = options.onEscape = function() {
                return options.callback(false);
            };
            options.buttons.cancel.className = "btn-default";

            options.buttons.confirm.callback = function() {
                return options.callback(true);
            };

            if (!options.title) {
                options.title = "确认框";
            }

            return this.dialog(options);
        },

        success: function() {
            var options = this.mergeMessageOptions("success", ["message", "callback", "timeout"], arguments);

            return this.box(options);
        },

        warning: function() {
            var options = this.mergeMessageOptions("warning", ["message", "callback", "timeout"], arguments);

            return this.box(options);
        },

        danger: function() {
            var options = this.mergeMessageOptions("danger", ["message", "callback", "timeout"], arguments);

            return this.box(options);
        },

        info: function() {
            var options = this.mergeMessageOptions("info", ["message", "callback", "timeout"], arguments);

            return this.box(options);
        },

        loading: function() {
            var option = arguments[0];
            if ("close" == option || false == option || "false" == option) {
                $(".loading-message").remove();
                $(".dialog-backdrop").remove();
            } else {
                if ($(".loading-message").length == 0) {
                    $("body").append(templates.loading);
                }
            }
        },

        box: function(options) {
            $.notify(options);
        },

        dialog: function(options) {
            var dialog = $(templates.dialog.frame),
                footer = dialog.find(".modal-footer"),
                callbacks = {
                    onEscape: options.onEscape
                };


            $.each(options.buttons, function(index, button) {
                footer.append("<button type='button' data-callback-key='" + index + "' class='btn " + button.className + "'>" + button.label + "</button>");
                callbacks[index] = button.callback;
            });

            dialog.find(".modal-body").html(options.message);

            if (options.title) {
                dialog.find(".modal-title").html(options.title);
            }

            if (options.closeButton) {
                dialog.find(".modal-header").prepend(templates.dialog.closeButton);
            }

            if (options.className) {
                dialog.addClass(options.className);
            }

            dialog.on("hidden.bs.modal", function(e) {
                if (e.target === this) {
                    dialog.remove();
                }
            });

            dialog.on("shown.bs.modal", function() {
                dialog.find(".btn-primary:first").focus();
            });

            /*Bind escape event*/
            dialog.on("click", "button.close", function(e) {
                exports.processCallback(e, dialog, callbacks.onEscape);
            });

            /*Bind button events*/
            dialog.on("click", ".modal-footer button", function(e) {
                var key = $(this).data("callback-key");
                exports.processCallback(e, dialog, callbacks[key]);
            });

            $(defaults.container).append(dialog);

            dialog.modal({
                backdrop: defaults.backdrop
            });

            return dialog;
        },

        mergeMessageOptions: function(status, properties, args) {
            var baseOptions = {
                status: status,
                pos: "top-right"
            };

            var mapedArgs = this.mapMessageArguments(args, properties);

            switch (status) {
                case 'success':
                    mapedArgs['message'] = "<em class='fa fa-check fa-lg'></em> " + mapedArgs['message'];
                    break;
                case 'info':
                    mapedArgs['message'] = "<em class='fa fa-info fa-lg'></em> " + mapedArgs['message'];
                    break;
                case 'warning':
                    mapedArgs['message'] = "<em class='fa fa-exclamation fa-lg'></em> " + mapedArgs['message'];
                    break;
                case 'danger':
                    mapedArgs['message'] = "<em class='fa fa-times fa-lg'></em> " + mapedArgs['message'];
                    break;
            }

            return $.extend(true, {}, baseOptions, mapedArgs);
        },

        mergeDialogOptions: function(className, labels, properties, args) {
            var baseOptions = {
                className: "dialog-" + className,
                buttons: this.mapDialogButtons(labels),
                closeButton: true
            };
            return $.extend(true, {}, baseOptions, this.mapDialogArguments(args, properties));
        },

        mapDialogButtons: function(labels) {
            var buttons = {};

            for (var i = 0, total = labels.length; i < total; i++) {
                var label = labels[i];
                if (typeof label === "string") {
                    buttons[label.toLowerCase()] = {
                        label: exports.parseLanguage(label.toUpperCase()),
                        className: "btn-primary"
                    };
                }
            }
            return buttons;
        },

        //["message", "callback", "expire"]
        mapMessageArguments: function(args, properties) {
            var length = args.length,
                options = {};

            if (length === 1 && typeof args[0] === "string") {
                options[properties[0]] = args[0];
            } else if (length === 2
                && typeof args[0] === "string"
                && typeof args[1] === "function") {
                options[properties[0]] = args[0];
                options[properties[1]] = args[1];
            } else if (length === 2
                && typeof args[0] === "string"
                && typeof args[1] === "number") {
                options[properties[0]] = args[0];
                options[properties[2]] = args[1];
            } else if (length === 3
                && typeof args[0] === "string"
                && typeof args[1] === "function"
                && typeof args[2] === "number") {
                options[properties[0]] = args[0];
                options[properties[1]] = args[1];
                options[properties[2]] = args[2];
            } else {
                throw new Error("Invalid argument length!");
            }

            return options;
        },

        mapDialogArguments: function(args, properties) {
            var length = args.length,
                options = {};

            if (length === 1 && typeof args[0] === "string") {
                options[properties[1]] = args[0];
            } else if (length === 2
                && typeof args[0] === "string"
                && typeof args[1] === "string") {
                options[properties[0]] = args[0];
                options[properties[1]] = args[1];
            } else if (length === 2
                && typeof args[0] === "string"
                && typeof args[1] === "function") {
                options[properties[1]] = args[0];
                options[properties[2]] = args[1];
            } else if (length === 3
                && typeof args[0] === "string"
                && typeof args[1] === "string"
                && typeof args[2] === "function") {
                options[properties[0]] = args[0];
                options[properties[1]] = args[1];
                options[properties[2]] = args[2];
            } else {
                throw new Error("Invalid argument length");
            }
            return options;
        },

        parseLanguage: function(key) {
            var locale = locales[defaults.locale];
            return locale ? locale[key] : locale.zh_CN[key];
        },

        processCallback: function (e, dialog, callback) {
            e.stopPropagation();
            e.preventDefault();

            if (!($.isFunction(callback) && callback(e) === false)) {
                dialog.modal("hide");
            }
        }
    };

    return exports;

}));
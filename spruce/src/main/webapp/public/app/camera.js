$(function () {
    var equipmentForm = $("#equipmentForm");
    equipmentForm.find(".add-equipment").click(function () {
        var id = $(this).attr("data-id");
        equipmentForm.find("#equipmentFormControls").append(tmpl(id + "_tml", {
            index: 0
        }));
        reDeleteAction();
    });


    function reDeleteAction() {
        equipmentForm.find(".delete").click(function () {
            var $this = $(this);
            var id = $this.attr("data-id");
            var cb = function () {
                $this.parent().parent().remove();
            }
            if (id) {
                $.delete("/settings/camera/{id}/delete".replace("{id}", id), cb);
            } else {
                cb();
            }
        });
    }

    reDeleteAction();

});

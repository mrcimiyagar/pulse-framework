package kasper.android.pulseframework.engines;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

import java.util.Hashtable;

import kasper.android.pulseframework.interfaces.IAnimToUpdate;
import kasper.android.pulseframework.locks.Locks;
import kasper.android.pulseframework.models.Anims;
import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Updates;
import kasper.android.pulseframework.utils.GraphicsHelper;

public class UiAnimatorEngine {

    private IAnimToUpdate animToUpdate;

    public UiAnimatorEngine(IAnimToUpdate animToUpdate) {
        this.animToUpdate = animToUpdate;
    }

    private void animateUiAsync(Controls.Control control, View view, Anims.Anim anim) {
        ValueAnimator valueAnimator = new ValueAnimator();
        if (anim instanceof Anims.ControlAnimX) {
            valueAnimator = ValueAnimator.ofFloat(view.getX()
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimX) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                float value = (float) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateX updateX = new Updates.ControlUpdateX();
                updateX.setControlId(anim.getControlId());
                updateX.setValue((int)value);
                animToUpdate.update(updateX);
            });
        } else if (anim instanceof Anims.ControlAnimY) {
            valueAnimator = ValueAnimator.ofFloat(view.getY()
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimY) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                float value = (float) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateY updateY = new Updates.ControlUpdateY();
                updateY.setControlId(anim.getControlId());
                updateY.setValue((int)value);
                animToUpdate.update(updateY);
            });
        } else if (anim instanceof Anims.ControlAnimWidth) {
            valueAnimator = ValueAnimator.ofInt(view.getMeasuredWidth()
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimWidth) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateWidth updateWidth = new Updates.ControlUpdateWidth();
                updateWidth.setControlId(anim.getControlId());
                updateWidth.setValue(value);
                animToUpdate.update(updateWidth);
            });
        } else if (anim instanceof Anims.ControlAnimHeight) {
            valueAnimator = ValueAnimator.ofInt(view.getMeasuredHeight()
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimHeight) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateHeight updateHeight = new Updates.ControlUpdateHeight();
                updateHeight.setControlId(anim.getControlId());
                updateHeight.setValue(value);
                animToUpdate.update(updateHeight);
            });
        } else if (anim instanceof Anims.ControlAnimMarginLeft) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getMarginLeft())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimMarginLeft) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateMarginLeft updateMarginLeft = new Updates.ControlUpdateMarginLeft();
                updateMarginLeft.setControlId(anim.getControlId());
                updateMarginLeft.setValue(value);
                animToUpdate.update(updateMarginLeft);
            });
        } else if (anim instanceof Anims.ControlAnimMarginRight) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getMarginRight())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimMarginRight) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateMarginRight updateMarginRight = new Updates.ControlUpdateMarginRight();
                updateMarginRight.setControlId(anim.getControlId());
                updateMarginRight.setValue(value);
                animToUpdate.update(updateMarginRight);
            });
        } else if (anim instanceof Anims.ControlAnimMarginTop) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getMarginTop())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimMarginTop) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateMarginTop updateMarginTop = new Updates.ControlUpdateMarginTop();
                updateMarginTop.setControlId(anim.getControlId());
                updateMarginTop.setValue(value);
                animToUpdate.update(updateMarginTop);
            });
        } else if (anim instanceof Anims.ControlAnimMarginBottom) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getMarginBottom())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimMarginBottom) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateMarginBottom updateMarginBottom = new Updates.ControlUpdateMarginBottom();
                updateMarginBottom.setControlId(anim.getControlId());
                updateMarginBottom.setValue(value);
                animToUpdate.update(updateMarginBottom);
            });
        } else if (anim instanceof Anims.ControlAnimPaddingLeft) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getPaddingLeft())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimPaddingLeft) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdatePaddingLeft updatePaddingLeft = new Updates.ControlUpdatePaddingLeft();
                updatePaddingLeft.setControlId(anim.getControlId());
                updatePaddingLeft.setValue(value);
                animToUpdate.update(updatePaddingLeft);
            });
        } else if (anim instanceof Anims.ControlAnimPaddingTop) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getPaddingTop())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimPaddingTop) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdatePaddingTop updatePaddingTop = new Updates.ControlUpdatePaddingTop();
                updatePaddingTop.setControlId(anim.getControlId());
                updatePaddingTop.setValue(value);
                animToUpdate.update(updatePaddingTop);
            });
        } else if (anim instanceof Anims.ControlAnimPaddingRight) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getPaddingRight())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimPaddingRight) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdatePaddingRight updatePaddingRight = new Updates.ControlUpdatePaddingRight();
                updatePaddingRight.setControlId(anim.getControlId());
                updatePaddingRight.setValue(value);
                animToUpdate.update(updatePaddingRight);
            });
        } else if (anim instanceof Anims.ControlAnimPaddingBottom) {
            valueAnimator = ValueAnimator.ofInt(GraphicsHelper.dpToPx(control.getPaddingBottom())
                    , GraphicsHelper.dpToPx(((Anims.ControlAnimPaddingBottom) anim).getFinalValue()));
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdatePaddingBottom updatePaddingBottom = new Updates.ControlUpdatePaddingBottom();
                updatePaddingBottom.setControlId(anim.getControlId());
                updatePaddingBottom.setValue(value);
                animToUpdate.update(updatePaddingBottom);
            });
        } else if (anim instanceof Anims.ControlAnimRotationX) {
            valueAnimator = ValueAnimator.ofInt(control.getRotationX()
                    , ((Anims.ControlAnimRotationX) anim).getFinalValue());
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateRotationX updateRotationX = new Updates.ControlUpdateRotationX();
                updateRotationX.setControlId(anim.getControlId());
                updateRotationX.setValue(value);
                animToUpdate.update(updateRotationX);
            });
        } else if (anim instanceof Anims.ControlAnimRotationY) {
            valueAnimator = ValueAnimator.ofInt(control.getRotationY()
                    , ((Anims.ControlAnimRotationY) anim).getFinalValue());
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateRotationY updateRotationY = new Updates.ControlUpdateRotationY();
                updateRotationY.setControlId(anim.getControlId());
                updateRotationY.setValue(value);
                animToUpdate.update(updateRotationY);
            });
        } else if (anim instanceof Anims.ControlAnimRotation) {
            valueAnimator = ValueAnimator.ofInt(control.getRotation()
                    , ((Anims.ControlAnimRotation) anim).getFinalValue());
            valueAnimator.addUpdateListener(valueAnimator1 -> {
                int value = (int) valueAnimator1.getAnimatedValue();
                Updates.ControlUpdateRotation updateRotation = new Updates.ControlUpdateRotation();
                updateRotation.setControlId(anim.getControlId());
                updateRotation.setValue(value);
                animToUpdate.update(updateRotation);

            });
        }
        valueAnimator.setDuration(anim.getDuration());
        valueAnimator.start();
    }

    public void animateUi(Hashtable<String, Pair<Controls.Control, View>> idTable, Anims.Anim anim) {
        Locks.runSafeOnIdTable(() -> {
            Pair<Controls.Control, View> pair = idTable.get(anim.getControlId());
            Controls.Control control = pair.first;
            View view = pair.second;
            new Handler().post(() -> animateUiAsync(control, view, anim));
        });
    }
}

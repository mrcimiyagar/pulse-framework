package kasper.android.pulseframework.models;

public class Anims {

    public static class Anim {
        private String controlId;
        private long duration;

        public String getControlId() {
            return controlId;
        }

        public void setControlId(String controlId) {
            this.controlId = controlId;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }
    }

    public static class IntAnim extends Anim {
        private int finalValue;

        public int getFinalValue() {
            return finalValue;
        }

        public void setFinalValue(int finalValue) {
            this.finalValue = finalValue;
        }
    }

    public static class ControlAnimX extends IntAnim {

    }

    public static class ControlAnimY extends IntAnim {

    }

    public static class ControlAnimWidth extends IntAnim {

    }

    public static class ControlAnimHeight extends IntAnim {

    }

    public static class ControlAnimMarginLeft extends IntAnim {

    }

    public static class ControlAnimMarginRight extends IntAnim {

    }

    public static class ControlAnimMarginTop extends IntAnim {

    }

    public static class ControlAnimMarginBottom extends IntAnim {

    }

    public static class ControlAnimPaddingLeft extends IntAnim {

    }

    public static class ControlAnimPaddingTop extends IntAnim {

    }

    public static class ControlAnimPaddingBottom extends IntAnim {

    }

    public static class ControlAnimPaddingRight extends IntAnim {

    }

    public static class ControlAnimRotation extends IntAnim {

    }

    public static class ControlAnimRotationX extends IntAnim {

    }

    public static class ControlAnimRotationY extends IntAnim {

    }
}

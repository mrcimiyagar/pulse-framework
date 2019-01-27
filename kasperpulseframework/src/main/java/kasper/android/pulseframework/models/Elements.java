package kasper.android.pulseframework.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

public class Elements {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = PanelEl.class, name = "PanelEl"),
            @JsonSubTypes.Type(value = TextEl.class, name = "TextEl"),
            @JsonSubTypes.Type(value = ImageEl.class, name = "ImageEl"),
            @JsonSubTypes.Type(value = InputFieldEl.class, name = "InputFieldEl"),
            @JsonSubTypes.Type(value = ButtonEl.class, name = "ButtonEl"),
            @JsonSubTypes.Type(value = ProgressEl.class, name = "ProgressEl"),
            @JsonSubTypes.Type(value = VideoPlayerEl.class, name = "VideoPlayerEl"),
            @JsonSubTypes.Type(value = LineChartEl.class, name = "LineChartEl"),
            @JsonSubTypes.Type(value = BarChartEl.class, name = "BarChartEl"),
            @JsonSubTypes.Type(value = HorizontalBarChartEl.class, name = "HorizontalBarChartEl"),
            @JsonSubTypes.Type(value = StackBarChartEl.class, name = "StackBarChartEl"),
            @JsonSubTypes.Type(value = HorizontalStackBarChartEl.class, name = "HorizontalStackBarChartEl"),
            @JsonSubTypes.Type(value = ScrollerEl.class, name = "ScrollerEl")
    })
    public static class Element {

        public static int MATCH_PARENT = -1;
        public static int WRAP_CONTENT = -2;

        public static int CENTER = -1;

        private int width;
        private int height;
        private int x;
        private int y;
        private String backColor;
        private String borderColor;
        private int borderWidth;
        private int rotationX;
        private int rotationY;
        private int rotation;
        private int cornerRadius;
        private int marginLeft;
        private int marginTop;
        private int marginRight;
        private int marginBottom;
        private int paddingLeft;
        private int paddingTop;
        private int paddingRight;
        private int paddingBottom;
        private int elevation;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getBackColor() {
            return backColor;
        }

        public void setBackColor(String backColor) {
            this.backColor = backColor;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public int getBorderWidth() {
            return borderWidth;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public int getRotationX() {
            return rotationX;
        }

        public void setRotationX(int rotationX) {
            this.rotationX = rotationX;
        }

        public int getRotationY() {
            return rotationY;
        }

        public void setRotationY(int rotationY) {
            this.rotationY = rotationY;
        }

        public int getRotation() {
            return rotation;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
        }

        public int getCornerRadius() {
            return cornerRadius;
        }

        public void setCornerRadius(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        public static int getMatchParent() {
            return MATCH_PARENT;
        }

        public static void setMatchParent(int matchParent) {
            MATCH_PARENT = matchParent;
        }

        public static int getWrapContent() {
            return WRAP_CONTENT;
        }

        public static void setWrapContent(int wrapContent) {
            WRAP_CONTENT = wrapContent;
        }

        public int getMarginLeft() {
            return marginLeft;
        }

        public void setMarginLeft(int marginLeft) {
            this.marginLeft = marginLeft;
        }

        public int getMarginTop() {
            return marginTop;
        }

        public void setMarginTop(int marginTop) {
            this.marginTop = marginTop;
        }

        public int getMarginRight() {
            return marginRight;
        }

        public void setMarginRight(int marginRight) {
            this.marginRight = marginRight;
        }

        public int getMarginBottom() {
            return marginBottom;
        }

        public void setMarginBottom(int marginBottom) {
            this.marginBottom = marginBottom;
        }

        public int getPaddingLeft() {
            return paddingLeft;
        }

        public void setPaddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
        }

        public int getPaddingTop() {
            return paddingTop;
        }

        public void setPaddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
        }

        public int getPaddingRight() {
            return paddingRight;
        }

        public void setPaddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
        }

        public int getPaddingBottom() {
            return paddingBottom;
        }

        public void setPaddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
        }

        public int getElevation() {
            return elevation;
        }

        public void setElevation(int elevation) {
            this.elevation = elevation;
        }
    }

    public static class PanelEl extends Element {

        public enum LayoutType {
            @JsonProperty("NONE") NONE,
            @JsonProperty("RELATIVE") RELATIVE,
            @JsonProperty("LINEAR_VERTICAL") LINEAR_VERTICAL,
            @JsonProperty("LINEAR_HORIZONTAL") LINEAR_HORIZONTAL,
        }

        private LayoutType layoutType;
        private List<Element> elements;

        public PanelEl() {
            this.elements = new ArrayList<>();
        }

        public LayoutType getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(LayoutType layoutType) {
            this.layoutType = layoutType;
        }

        public List<Element> getElements() {
            return elements;
        }

        public void setElements(List<Element> elements) {
            this.elements = elements;
        }
    }

    public static class TextEl extends Element {

        public enum GravityType {
            @JsonProperty("NONE") NONE,
            @JsonProperty("LEFT") LEFT,
            @JsonProperty("TOP") TOP,
            @JsonProperty("RIGHT") RIGHT,
            @JsonProperty("BOTTOM") BOTTOM,
            @JsonProperty("CENTER_VERTICAL") CENTER_VERTICAL,
            @JsonProperty("CENTER_HORIZONTAL") CENTER_HORIZONTAL,
            @JsonProperty("CENTER") CENTER
        }

        private String text;
        private int textSize;
        private String textColor;
        private GravityType gravityType;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public String getTextColor() {
            return textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = textColor;
        }

        public GravityType getGravityType() {
            return gravityType;
        }

        public void setGravityType(GravityType gravityType) {
            this.gravityType = gravityType;
        }
    }

    public static class ImageEl extends Element {
        public enum ImageScaleType {
            @JsonProperty("NONE") NONE,
            @JsonProperty("CENTER") CENTER,
            @JsonProperty("CENTER_CROP") CENTER_CROP,
            @JsonProperty("CENTER_INSIDE") CENTER_INSIDE,
            @JsonProperty("FIT_CENTER") FIT_CENTER,
            @JsonProperty("FIT_XY") FIT_XY,
            @JsonProperty("FIT_START") FIT_START,
            @JsonProperty("FIT_END") FIT_END,
            @JsonProperty("MATRIX") MATRIX
        }
        private String imageUrl;
        private ImageScaleType scaleType;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public ImageScaleType getScaleType() {
            return scaleType;
        }

        public void setScaleType(ImageScaleType scaleType) {
            this.scaleType = scaleType;
        }
    }

    public static class InputFieldEl extends Element {
        private String hint;
        private String hintColor;
        private String textColor;
        private int textSize;
        private String lineColor;

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getHintColor() {
            return hintColor;
        }

        public void setHintColor(String hintColor) {
            this.hintColor = hintColor;
        }

        public String getTextColor() {
            return textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = textColor;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public String getLineColor() {
            return lineColor;
        }

        public void setLineColor(String lineColor) {
            this.lineColor = lineColor;
        }
    }

    public static class ButtonEl extends Element {
        private String caption;
        private int captionSize;
        private String captionColor;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public int getCaptionSize() {
            return captionSize;
        }

        public void setCaptionSize(int captionSize) {
            this.captionSize = captionSize;
        }

        public String getCaptionColor() {
            return captionColor;
        }

        public void setCaptionColor(String captionColor) {
            this.captionColor = captionColor;
        }
    }

    public static class ProgressEl extends Element {
        public enum ProgressType {
            @JsonProperty("HORIZONTAL") HORIZONTAL,
            @JsonProperty("CIRCULAR") CIRCULAR
        }
        private ProgressType progressType;
        private boolean circleBroken;
        private int trackWidth;
        private boolean fillEnabled;
        private int progressTextSize;
        private String progressTextColor;
        private boolean trackEnabled;
        private String trackColor;
        private boolean progressTextVisibility;
        private boolean graduatedEnabled;
        private int scaleZoneWidth;
        private int scaleZoneLength;
        private int scaleZonePadding;
        private int scaleZoneCornerRadius;
        private int progressCornerRadius;
        private int progressTextPaddingBottom;
        private boolean progressTextMoved;

        public ProgressType getProgressType() {
            return progressType;
        }

        public void setProgressType(ProgressType progressType) {
            this.progressType = progressType;
        }

        public boolean isCircleBroken() {
            return circleBroken;
        }

        public void setCircleBroken(boolean circleBroken) {
            this.circleBroken = circleBroken;
        }

        public int getTrackWidth() {
            return trackWidth;
        }

        public void setTrackWidth(int trackWidth) {
            this.trackWidth = trackWidth;
        }

        public boolean isFillEnabled() {
            return fillEnabled;
        }

        public void setFillEnabled(boolean fillEnabled) {
            this.fillEnabled = fillEnabled;
        }

        public int getProgressTextSize() {
            return progressTextSize;
        }

        public void setProgressTextSize(int progressTextSize) {
            this.progressTextSize = progressTextSize;
        }

        public String getProgressTextColor() {
            return progressTextColor;
        }

        public void setProgressTextColor(String progressTextColor) {
            this.progressTextColor = progressTextColor;
        }

        public boolean isTrackEnabled() {
            return trackEnabled;
        }

        public void setTrackEnabled(boolean trackEnabled) {
            this.trackEnabled = trackEnabled;
        }

        public String getTrackColor() {
            return trackColor;
        }

        public void setTrackColor(String trackColor) {
            this.trackColor = trackColor;
        }

        public boolean isProgressTextVisibility() {
            return progressTextVisibility;
        }

        public void setProgressTextVisibility(boolean progressTextVisibility) {
            this.progressTextVisibility = progressTextVisibility;
        }

        public boolean isGraduatedEnabled() {
            return graduatedEnabled;
        }

        public void setGraduatedEnabled(boolean graduatedEnabled) {
            this.graduatedEnabled = graduatedEnabled;
        }

        public int getScaleZoneWidth() {
            return scaleZoneWidth;
        }

        public void setScaleZoneWidth(int scaleZoneWidth) {
            this.scaleZoneWidth = scaleZoneWidth;
        }

        public int getScaleZoneLength() {
            return scaleZoneLength;
        }

        public void setScaleZoneLength(int scaleZoneLength) {
            this.scaleZoneLength = scaleZoneLength;
        }

        public int getScaleZonePadding() {
            return scaleZonePadding;
        }

        public void setScaleZonePadding(int scaleZonePadding) {
            this.scaleZonePadding = scaleZonePadding;
        }

        public int getScaleZoneCornerRadius() {
            return scaleZoneCornerRadius;
        }

        public void setScaleZoneCornerRadius(int scaleZoneCornerRadius) {
            this.scaleZoneCornerRadius = scaleZoneCornerRadius;
        }

        public int getProgressCornerRadius() {
            return progressCornerRadius;
        }

        public void setProgressCornerRadius(int progressCornerRadius) {
            this.progressCornerRadius = progressCornerRadius;
        }

        public int getProgressTextPaddingBottom() {
            return progressTextPaddingBottom;
        }

        public void setProgressTextPaddingBottom(int progressTextPaddingBottom) {
            this.progressTextPaddingBottom = progressTextPaddingBottom;
        }

        public boolean isProgressTextMoved() {
            return progressTextMoved;
        }

        public void setProgressTextMoved(boolean progressTextMoved) {
            this.progressTextMoved = progressTextMoved;
        }
    }

    public static class VideoPlayerEl extends Element {
        private String videoUrl;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }

    public static class LineChartEl extends Element {
        private List<Data.Point> points;
        private String dotsColor;
        private float dotsRadius;
        private float dotsStrokeThickness;
        private String dotsStrokeColor;
        private List<Data.FloatValue> lineDashedIntervals;
        private boolean lineSmooth;
        private float lineThickness;
        private String lineColor;
        private int lineBeginAt;
        private int lineEndAt;
        private String fillColor;
        private List<Data.StringValue> gradientColors;
        private List<Data.FloatValue> gradientValues;
        private String axisColor;
        private String labelsColor;

        public List<Data.Point> getPoints() {
            return points;
        }

        public void setPoints(List<Data.Point> points) {
            this.points = points;
        }

        public String getDotsColor() {
            return dotsColor;
        }

        public void setDotsColor(String dotsColor) {
            this.dotsColor = dotsColor;
        }

        public float getDotsRadius() {
            return dotsRadius;
        }

        public void setDotsRadius(float dotsRadius) {
            this.dotsRadius = dotsRadius;
        }

        public float getDotsStrokeThickness() {
            return dotsStrokeThickness;
        }

        public void setDotsStrokeThickness(float dotsStrokeThickness) {
            this.dotsStrokeThickness = dotsStrokeThickness;
        }

        public String getDotsStrokeColor() {
            return dotsStrokeColor;
        }

        public void setDotsStrokeColor(String dotsStrokeColor) {
            this.dotsStrokeColor = dotsStrokeColor;
        }

        public List<Data.FloatValue> getLineDashedIntervals() {
            return lineDashedIntervals;
        }

        public void setLineDashedIntervals(List<Data.FloatValue> lineDashedIntervals) {
            this.lineDashedIntervals = lineDashedIntervals;
        }

        public boolean isLineSmooth() {
            return lineSmooth;
        }

        public void setLineSmooth(boolean lineSmooth) {
            this.lineSmooth = lineSmooth;
        }

        public float getLineThickness() {
            return lineThickness;
        }

        public void setLineThickness(float lineThickness) {
            this.lineThickness = lineThickness;
        }

        public String getLineColor() {
            return lineColor;
        }

        public void setLineColor(String lineColor) {
            this.lineColor = lineColor;
        }

        public int getLineBeginAt() {
            return lineBeginAt;
        }

        public void setLineBeginAt(int lineBeginAt) {
            this.lineBeginAt = lineBeginAt;
        }

        public int getLineEndAt() {
            return lineEndAt;
        }

        public void setLineEndAt(int lineEndAt) {
            this.lineEndAt = lineEndAt;
        }

        public String getFillColor() {
            return fillColor;
        }

        public void setFillColor(String fillColor) {
            this.fillColor = fillColor;
        }

        public List<Data.StringValue> getGradientColors() {
            return gradientColors;
        }

        public void setGradientColors(List<Data.StringValue> gradientColors) {
            this.gradientColors = gradientColors;
        }

        public List<Data.FloatValue> getGradientValues() {
            return gradientValues;
        }

        public void setGradientValues(List<Data.FloatValue> gradientValues) {
            this.gradientValues = gradientValues;
        }

        public String getAxisColor() {
            return axisColor;
        }

        public void setAxisColor(String axisColor) {
            this.axisColor = axisColor;
        }

        public String getLabelsColor() {
            return labelsColor;
        }

        public void setLabelsColor(String labelsColor) {
            this.labelsColor = labelsColor;
        }
    }

    public static class BarChartEl extends Element {
        private List<Data.Point> points;
        private int barSpacing;
        private int setSpacing;
        private String barBackgroundColor;
        private int roundCorners;
        private String barsColor;
        private List<Data.StringValue> barColors;
        private String axisColor;
        private String labelsColor;

        public List<Data.Point> getPoints() {
            return points;
        }

        public void setPoints(List<Data.Point> points) {
            this.points = points;
        }

        public int getBarSpacing() {
            return barSpacing;
        }

        public void setBarSpacing(int barSpacing) {
            this.barSpacing = barSpacing;
        }

        public int getSetSpacing() {
            return setSpacing;
        }

        public void setSetSpacing(int setSpacing) {
            this.setSpacing = setSpacing;
        }

        public String getBarBackgroundColor() {
            return barBackgroundColor;
        }

        public void setBarBackgroundColor(String barBackgroundColor) {
            this.barBackgroundColor = barBackgroundColor;
        }

        public int getRoundCorners() {
            return roundCorners;
        }

        public void setRoundCorners(int roundCorners) {
            this.roundCorners = roundCorners;
        }

        public String getBarsColor() {
            return barsColor;
        }

        public void setBarsColor(String barsColor) {
            this.barsColor = barsColor;
        }

        public List<Data.StringValue> getBarColors() {
            return barColors;
        }

        public void setBarColors(List<Data.StringValue> barColors) {
            this.barColors = barColors;
        }

        public String getAxisColor() {
            return axisColor;
        }

        public void setAxisColor(String axisColor) {
            this.axisColor = axisColor;
        }

        public String getLabelsColor() {
            return labelsColor;
        }

        public void setLabelsColor(String labelsColor) {
            this.labelsColor = labelsColor;
        }
    }

    public static class HorizontalBarChartEl extends Element {
        private List<Data.Point> points;
        private int barSpacing;
        private int setSpacing;
        private String barBackgroundColor;
        private int roundCorners;
        private String barsColor;
        private List<Data.StringValue> barColors;
        private String axisColor;
        private String labelsColor;

        public List<Data.Point> getPoints() {
            return points;
        }

        public void setPoints(List<Data.Point> points) {
            this.points = points;
        }

        public int getBarSpacing() {
            return barSpacing;
        }

        public void setBarSpacing(int barSpacing) {
            this.barSpacing = barSpacing;
        }

        public int getSetSpacing() {
            return setSpacing;
        }

        public void setSetSpacing(int setSpacing) {
            this.setSpacing = setSpacing;
        }

        public String getBarBackgroundColor() {
            return barBackgroundColor;
        }

        public void setBarBackgroundColor(String barBackgroundColor) {
            this.barBackgroundColor = barBackgroundColor;
        }

        public int getRoundCorners() {
            return roundCorners;
        }

        public void setRoundCorners(int roundCorners) {
            this.roundCorners = roundCorners;
        }

        public String getBarsColor() {
            return barsColor;
        }

        public void setBarsColor(String barsColor) {
            this.barsColor = barsColor;
        }

        public List<Data.StringValue> getBarColors() {
            return barColors;
        }

        public void setBarColors(List<Data.StringValue> barColors) {
            this.barColors = barColors;
        }

        public String getAxisColor() {
            return axisColor;
        }

        public void setAxisColor(String axisColor) {
            this.axisColor = axisColor;
        }

        public String getLabelsColor() {
            return labelsColor;
        }

        public void setLabelsColor(String labelsColor) {
            this.labelsColor = labelsColor;
        }
    }

    public static class StackBarChartEl extends Element {
        private int barSpacing;
        private String barBackgroundColor;
        private int roundCorners;
        private String barsColor;
        private List<Data.StringValue> barColors;
        private List<Data.Point> points;
        private String axisColor;
        private String labelsColor;

        public int getBarSpacing() {
            return barSpacing;
        }

        public void setBarSpacing(int barSpacing) {
            this.barSpacing = barSpacing;
        }

        public String getBarBackgroundColor() {
            return barBackgroundColor;
        }

        public void setBarBackgroundColor(String barBackgroundColor) {
            this.barBackgroundColor = barBackgroundColor;
        }

        public int getRoundCorners() {
            return roundCorners;
        }

        public void setRoundCorners(int roundCorners) {
            this.roundCorners = roundCorners;
        }

        public String getBarsColor() {
            return barsColor;
        }

        public void setBarsColor(String barsColor) {
            this.barsColor = barsColor;
        }

        public List<Data.StringValue> getBarColors() {
            return barColors;
        }

        public void setBarColors(List<Data.StringValue> barColors) {
            this.barColors = barColors;
        }

        public List<Data.Point> getPoints() {
            return points;
        }

        public void setPoints(List<Data.Point> points) {
            this.points = points;
        }

        public String getAxisColor() {
            return axisColor;
        }

        public void setAxisColor(String axisColor) {
            this.axisColor = axisColor;
        }

        public String getLabelsColor() {
            return labelsColor;
        }

        public void setLabelsColor(String labelsColor) {
            this.labelsColor = labelsColor;
        }
    }

    public static class HorizontalStackBarChartEl extends Element {
        private int barSpacing;
        private String barBackgroundColor;
        private int roundCorners;
        private String barsColor;
        private List<Data.StringValue> barColors;
        private List<Data.Point> points;
        private String axisColor;
        private String labelsColor;

        public int getBarSpacing() {
            return barSpacing;
        }

        public void setBarSpacing(int barSpacing) {
            this.barSpacing = barSpacing;
        }

        public String getBarBackgroundColor() {
            return barBackgroundColor;
        }

        public void setBarBackgroundColor(String barBackgroundColor) {
            this.barBackgroundColor = barBackgroundColor;
        }

        public int getRoundCorners() {
            return roundCorners;
        }

        public void setRoundCorners(int roundCorners) {
            this.roundCorners = roundCorners;
        }

        public String getBarsColor() {
            return barsColor;
        }

        public void setBarsColor(String barsColor) {
            this.barsColor = barsColor;
        }

        public List<Data.StringValue> getBarColors() {
            return barColors;
        }

        public void setBarColors(List<Data.StringValue> barColors) {
            this.barColors = barColors;
        }

        public List<Data.Point> getPoints() {
            return points;
        }

        public void setPoints(List<Data.Point> points) {
            this.points = points;
        }

        public String getAxisColor() {
            return axisColor;
        }

        public void setAxisColor(String axisColor) {
            this.axisColor = axisColor;
        }

        public String getLabelsColor() {
            return labelsColor;
        }

        public void setLabelsColor(String labelsColor) {
            this.labelsColor = labelsColor;
        }
    }

    public static class ScrollerEl extends Element {
        private PanelEl panel;

        public PanelEl getPanel() {
            return panel;
        }

        public void setPanel(PanelEl panel) {
            this.panel = panel;
        }
    }
}

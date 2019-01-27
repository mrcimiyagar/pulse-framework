package kasper.android.pulseframework.engine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.HorizontalBarChartView;
import com.db.chart.view.HorizontalStackBarChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.StackBarChartView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.moos.library.CircleProgressView;
import com.moos.library.HorizontalProgressView;

import java.util.Iterator;
import java.util.List;

import kasper.android.pulseframework.models.Data;
import kasper.android.pulseframework.models.Elements;
import kasper.android.pulseframework.utils.GraphicsHelper;

public class Renderer {

    private Context context;
    private String appName;

    public Renderer(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }

    public View render(Elements.PanelEl.LayoutType parentLayoutType, Elements.Element element) {
        View result = null;
        if (element instanceof Elements.PanelEl) {
            Elements.PanelEl panelEl = (Elements.PanelEl) element;
            if (panelEl.getLayoutType() == Elements.PanelEl.LayoutType.RELATIVE) {
                RelativeLayout relativeLayout = new RelativeLayout(context);
                for (Elements.Element el : panelEl.getElements()) {
                    View childView = render(panelEl.getLayoutType(), el);
                    relativeLayout.addView(childView);
                }
                result = relativeLayout;
            } else if (panelEl.getLayoutType() == Elements.PanelEl.LayoutType.LINEAR_VERTICAL) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for (Elements.Element el : panelEl.getElements()) {
                    View childView = render(panelEl.getLayoutType(), el);
                    linearLayout.addView(childView);
                }
                result = linearLayout;
            } else if (panelEl.getLayoutType() == Elements.PanelEl.LayoutType.LINEAR_HORIZONTAL) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (Elements.Element el : panelEl.getElements()) {
                    View childView = render(panelEl.getLayoutType(), el);
                    linearLayout.addView(childView);
                }
                result = linearLayout;
            }
        } else if (element instanceof Elements.TextEl) {
            Elements.TextEl textEl = (Elements.TextEl) element;
            TextView textView = new TextView(context);
            if (!isFieldEmpty(textEl.getText()))
                textView.setText(textEl.getText());
            if (!isFieldEmpty(textEl.getTextSize()))
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textEl.getTextSize());
            if (!isFieldEmpty(textEl.getTextColor()))
                textView.setTextColor(Color.parseColor(textEl.getTextColor()));
            if (!isFieldEmpty(textEl.getGravityType())) {
                if (textEl.getGravityType() == Elements.TextEl.GravityType.LEFT) {
                    textView.setGravity(Gravity.LEFT);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.TOP) {
                    textView.setGravity(Gravity.TOP);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.RIGHT) {
                    textView.setGravity(Gravity.RIGHT);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.BOTTOM) {
                    textView.setGravity(Gravity.BOTTOM);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.CENTER_VERTICAL) {
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.CENTER_HORIZONTAL) {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                } else if (textEl.getGravityType() == Elements.TextEl.GravityType.CENTER) {
                    textView.setGravity(Gravity.CENTER);
                }
            }
            result = textView;
        } else if (element instanceof Elements.ImageEl) {
            Elements.ImageEl imageEl = (Elements.ImageEl) element;
            ImageView imageView = new ImageView(context);
            if (!isFieldEmpty(imageEl.getScaleType()))
                imageView.setScaleType(ImageView.ScaleType.valueOf(
                        ImageView.ScaleType.class,
                        imageEl.getScaleType().toString()));
            if (!isFieldEmpty(imageEl.getImageUrl()))
                Glide.with(context)
                        .load(imageEl.getImageUrl())
                        .into(imageView);
            result = imageView;
        } else if (element instanceof Elements.InputFieldEl) {
            Elements.InputFieldEl inputFieldEl = (Elements.InputFieldEl) element;
            EditText editText = new EditText(context);
            if (!isFieldEmpty(inputFieldEl.getHint()))
                editText.setHint(inputFieldEl.getHint());
            if (!isFieldEmpty(inputFieldEl.getHintColor()))
                editText.setHintTextColor(Color.parseColor(inputFieldEl.getHintColor()));
            else
                editText.setHintTextColor(Color.GRAY);
            if (!isFieldEmpty(inputFieldEl.getTextColor()))
                editText.setTextColor(Color.parseColor(inputFieldEl.getTextColor()));
            else
                editText.setTextColor(Color.BLACK);
            if (!isFieldEmpty(inputFieldEl.getTextSize()))
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, inputFieldEl.getTextSize());
            else
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (!isFieldEmpty(inputFieldEl.getLineColor()))
                editText.getBackground().setColorFilter(
                        Color.parseColor(inputFieldEl.getLineColor()), PorterDuff.Mode.SRC_ATOP);
            result = editText;
        } else if (element instanceof Elements.ButtonEl) {
            Elements.ButtonEl buttonEl = (Elements.ButtonEl) element;
            Button button = new Button(context);
            if (!isFieldEmpty(buttonEl.getCaption()))
                button.setText(buttonEl.getCaption());
            if (!isFieldEmpty(buttonEl.getCaptionSize()))
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonEl.getCaptionSize());
            else
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (!isFieldEmpty(buttonEl.getCaptionColor()))
                button.setTextColor(Color.parseColor(buttonEl.getCaptionColor()));
            else
                button.setTextColor(Color.BLACK);
            result = button;
        } else if (element instanceof Elements.ProgressEl) {
            Elements.ProgressEl progressEl = (Elements.ProgressEl) element;
            if (progressEl.getProgressType() == Elements.ProgressEl.ProgressType.CIRCULAR) {
                CircleProgressView circleProgressView = new CircleProgressView(context);
                circleProgressView.setCircleBroken(progressEl.isCircleBroken());
                circleProgressView.setTrackWidth(progressEl.getTrackWidth());
                circleProgressView.setFillEnabled(progressEl.isFillEnabled());
                if (!isFieldEmpty(progressEl.getProgressTextSize()))
                    circleProgressView.setProgressTextSize(progressEl.getProgressTextSize());
                if (!isFieldEmpty(progressEl.getProgressTextColor()))
                    circleProgressView.setProgressTextColor(
                            Color.parseColor(progressEl.getProgressTextColor()));
                circleProgressView.setTrackEnabled(progressEl.isTrackEnabled());
                if (!isFieldEmpty(progressEl.getTrackColor()))
                    circleProgressView.setTrackColor(Color.parseColor(progressEl.getTrackColor()));
                circleProgressView.setProgressTextVisibility(progressEl.isProgressTextVisibility());
                circleProgressView.setGraduatedEnabled(progressEl.isGraduatedEnabled());
                circleProgressView.setScaleZoneWidth(
                        GraphicsHelper.dpToPx(progressEl.getScaleZoneWidth()));
                circleProgressView.setScaleZoneLength(
                        GraphicsHelper.dpToPx(progressEl.getScaleZoneLength()));
                circleProgressView.setScaleZonePadding(
                        GraphicsHelper.dpToPx(progressEl.getScaleZonePadding()));
                circleProgressView.setScaleZoneCornerRadius(
                        GraphicsHelper.dpToPx(progressEl.getScaleZoneCornerRadius()));
                result = circleProgressView;
            } else if (progressEl.getProgressType() == Elements.ProgressEl.ProgressType.HORIZONTAL) {
                HorizontalProgressView horizontalProgressView = new HorizontalProgressView(context);
                if (!isFieldEmpty(progressEl.getTrackWidth()))
                    horizontalProgressView.setTrackWidth(progressEl.getTrackWidth());
                if (!isFieldEmpty(progressEl.getProgressTextSize()))
                    horizontalProgressView.setProgressTextSize(progressEl.getProgressTextSize());
                if (!isFieldEmpty(progressEl.getProgressTextColor()))
                    horizontalProgressView.setProgressTextColor(
                            Color.parseColor(progressEl.getProgressTextColor()));
                horizontalProgressView.setTrackEnabled(progressEl.isTrackEnabled());
                if (!isFieldEmpty(progressEl.getTrackColor()))
                    horizontalProgressView.setTrackColor(Color.parseColor(progressEl.getTrackColor()));
                horizontalProgressView.setProgressTextVisibility(progressEl.isProgressTextVisibility());
                horizontalProgressView.setProgressCornerRadius(
                        GraphicsHelper.dpToPx(progressEl.getProgressCornerRadius()));
                if (!isFieldEmpty(progressEl.getProgressTextPaddingBottom()))
                    horizontalProgressView.setProgressTextPaddingBottom(
                            GraphicsHelper.dpToPx(progressEl.getProgressTextPaddingBottom()));
                horizontalProgressView.setProgressTextMoved(progressEl.isProgressTextMoved());
                result = horizontalProgressView;
            }
        } else if (element instanceof Elements.VideoPlayerEl) {
            Elements.VideoPlayerEl videoPlayerEl = (Elements.VideoPlayerEl) element;
            SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(context);
            PlayerView playerView = new PlayerView(context);
            playerView.setPlayer(player);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, appName));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoPlayerEl.getVideoUrl()));
            player.prepare(videoSource);
            result = playerView;
        } else if (element instanceof Elements.LineChartEl) {
            Elements.LineChartEl chartEl = (Elements.LineChartEl) element;
            LineChartView lineChartView = new LineChartView(context);
            LineSet dataset = new LineSet();
            for (Data.Point point : chartEl.getPoints()) {
                dataset.addPoint(point.getLabel(), point.getValue());
            }
            if (!isFieldEmpty(chartEl.getDotsColor()))
                dataset.setDotsColor(Color.parseColor(chartEl.getDotsColor()));
            if (!isFieldEmpty(chartEl.getDotsRadius()))
                dataset.setDotsRadius(GraphicsHelper.dpToPx(chartEl.getDotsRadius()));
            if (!isFieldEmpty(chartEl.getDotsStrokeThickness()))
                dataset.setDotsStrokeThickness(GraphicsHelper.dpToPx(chartEl.getDotsStrokeThickness()));
            if (!isFieldEmpty(chartEl.getDotsStrokeColor()))
                dataset.setDotsStrokeColor(Color.parseColor(chartEl.getDotsStrokeColor()));
            if (!isFieldEmpty(chartEl.getLineDashedIntervals())) {
                float[] lineDashedIntervals = new float[chartEl.getLineDashedIntervals().size()];
                int counter = 0;
                for (Data.FloatValue floatValue : chartEl.getLineDashedIntervals()) {
                    lineDashedIntervals[counter] = floatValue.getValue();
                    counter++;
                }
                dataset.setDashed(lineDashedIntervals);
            }
            dataset.setSmooth(chartEl.isLineSmooth());
            if (!isFieldEmpty(chartEl.getLineThickness()))
                dataset.setThickness(GraphicsHelper.dpToPx(chartEl.getLineThickness()));
            if (!isFieldEmpty(chartEl.getLineColor()))
                dataset.setColor(Color.parseColor(chartEl.getLineColor()));
            if (!(chartEl.getLineBeginAt() == 0 && chartEl.getLineEndAt() == 0)) {
                dataset.beginAt(chartEl.getLineBeginAt());
                dataset.endAt(chartEl.getLineEndAt());
            }
            if (!isFieldEmpty(chartEl.getFillColor()))
                dataset.setFill(Color.parseColor(chartEl.getFillColor()));
            if (!isFieldEmpty(chartEl.getGradientColors())) {
                int[] gradientColors = new int[chartEl.getGradientColors().size()];
                float[] gradientValues = new float[chartEl.getGradientValues().size()];
                int counter = 0;
                Iterator<Data.StringValue> colorIterator = chartEl.getGradientColors().iterator();
                Iterator<Data.FloatValue> valueIterator = chartEl.getGradientValues().iterator();
                while (colorIterator.hasNext() && valueIterator.hasNext()) {
                    Data.StringValue color = colorIterator.next();
                    Data.FloatValue value = valueIterator.next();
                    gradientColors[counter] = Color.parseColor(color.getValue());
                    gradientValues[counter] = value.getValue();
                    counter++;
                }
                if (gradientColors.length > 0)
                    dataset.setGradientFill(gradientColors, gradientValues);
            }
            lineChartView.addData(dataset);
            if (!isFieldEmpty(chartEl.getAxisColor()))
                lineChartView.setAxisColor(Color.parseColor(chartEl.getAxisColor()));
            if (!isFieldEmpty(chartEl.getLabelsColor()))
                lineChartView.setLabelsColor(Color.parseColor(chartEl.getLabelsColor()));
            lineChartView.setClickablePointRadius(GraphicsHelper.dpToPx(chartEl.getDotsRadius()));
            lineChartView.show();
            result = lineChartView;
        } else if (element instanceof Elements.BarChartEl) {
            Elements.BarChartEl chartEl = (Elements.BarChartEl) element;
            BarChartView barChartView = new BarChartView(context);
            if (!isFieldEmpty(chartEl.getBarSpacing()))
                barChartView.setBarSpacing(GraphicsHelper.dpToPx(chartEl.getBarSpacing()));
            if (!isFieldEmpty(chartEl.getSetSpacing()))
                barChartView.setSetSpacing(GraphicsHelper.dpToPx(chartEl.getSetSpacing()));
            if (!isFieldEmpty(chartEl.getBarBackgroundColor()))
                barChartView.setBarBackgroundColor(Color.parseColor(chartEl.getBarBackgroundColor()));
            if (!isFieldEmpty(chartEl.getRoundCorners()))
                barChartView.setRoundCorners(GraphicsHelper.dpToPx(chartEl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(chartEl.getBarsColor()))
                dataset.setColor(Color.parseColor(chartEl.getBarsColor()));
            Iterator<Data.Point> pointIterator = chartEl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator = chartEl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            barChartView.addData(dataset);
            if (!isFieldEmpty(chartEl.getAxisColor()))
                barChartView.setAxisColor(Color.parseColor(chartEl.getAxisColor()));
            if (!isFieldEmpty(chartEl.getLabelsColor()))
                barChartView.setLabelsColor(Color.parseColor(chartEl.getLabelsColor()));
            barChartView.show();
            result = barChartView;
        } else if (element instanceof Elements.HorizontalBarChartEl) {
            Elements.HorizontalBarChartEl chartEl = (Elements.HorizontalBarChartEl) element;
            HorizontalBarChartView horizontalBarChartView = new HorizontalBarChartView(context);
            if (!isFieldEmpty(chartEl.getBarSpacing()))
                horizontalBarChartView.setBarSpacing(GraphicsHelper.dpToPx(chartEl.getBarSpacing()));
            if (!isFieldEmpty(chartEl.getSetSpacing()))
                horizontalBarChartView.setSetSpacing(GraphicsHelper.dpToPx(chartEl.getSetSpacing()));
            if (!isFieldEmpty(chartEl.getBarBackgroundColor()))
                horizontalBarChartView.setBarBackgroundColor(Color.parseColor(chartEl.getBarBackgroundColor()));
            if (!isFieldEmpty(chartEl.getRoundCorners()))
                horizontalBarChartView.setRoundCorners(GraphicsHelper.dpToPx(chartEl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(chartEl.getBarsColor()))
                dataset.setColor(Color.parseColor(chartEl.getBarsColor()));
            Iterator<Data.Point> pointIterator = chartEl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator = chartEl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            horizontalBarChartView.addData(dataset);
            if (!isFieldEmpty(chartEl.getAxisColor()))
                horizontalBarChartView.setAxisColor(Color.parseColor(chartEl.getAxisColor()));
            if (!isFieldEmpty(chartEl.getLabelsColor()))
                horizontalBarChartView.setLabelsColor(Color.parseColor(chartEl.getLabelsColor()));
            horizontalBarChartView.show();
            result = horizontalBarChartView;
        } else if (element instanceof Elements.StackBarChartEl) {
            Elements.StackBarChartEl stackBarChartEl = (Elements.StackBarChartEl) element;
            StackBarChartView stackBarChartView = new StackBarChartView(context);
            if (!isFieldEmpty(stackBarChartEl.getBarSpacing()))
                stackBarChartView.setBarSpacing(GraphicsHelper.dpToPx(stackBarChartEl.getBarSpacing()));
            if (!isFieldEmpty(stackBarChartEl.getBarBackgroundColor()))
                stackBarChartView.setBarBackgroundColor(
                        Color.parseColor(stackBarChartEl.getBarBackgroundColor()));
            if (!isFieldEmpty(stackBarChartEl.getRoundCorners()))
                stackBarChartView.setRoundCorners(
                        GraphicsHelper.dpToPx(stackBarChartEl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(stackBarChartEl.getBarsColor()))
                dataset.setColor(Color.parseColor(stackBarChartEl.getBarsColor()));
            Iterator<Data.Point> pointIterator = stackBarChartEl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator = stackBarChartEl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            stackBarChartView.addData(dataset);
            if (!isFieldEmpty(stackBarChartEl.getAxisColor()))
                stackBarChartView.setAxisColor(Color.parseColor(stackBarChartEl.getAxisColor()));
            if (!isFieldEmpty(stackBarChartEl.getLabelsColor()))
                stackBarChartView.setLabelsColor(Color.parseColor(stackBarChartEl.getLabelsColor()));
            stackBarChartView.show();
            result = stackBarChartView;
        } else if (element instanceof Elements.HorizontalStackBarChartEl) {
            Elements.HorizontalStackBarChartEl horizontalStackBarChartEl =
                    (Elements.HorizontalStackBarChartEl) element;
            HorizontalStackBarChartView horizontalStackBarChartView =
                    new HorizontalStackBarChartView(context);
            if (!isFieldEmpty(horizontalStackBarChartEl.getBarSpacing()))
                horizontalStackBarChartView.setBarSpacing(
                        GraphicsHelper.dpToPx(horizontalStackBarChartEl.getBarSpacing()));
            if (!isFieldEmpty(horizontalStackBarChartEl.getBarBackgroundColor()))
                horizontalStackBarChartView.setBarBackgroundColor(
                        Color.parseColor(horizontalStackBarChartEl.getBarBackgroundColor()));
            if (!isFieldEmpty(horizontalStackBarChartEl.getRoundCorners()))
                horizontalStackBarChartView.setRoundCorners(
                        GraphicsHelper.dpToPx(horizontalStackBarChartEl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(horizontalStackBarChartEl.getBarsColor()))
                dataset.setColor(Color.parseColor(horizontalStackBarChartEl.getBarsColor()));
            Iterator<Data.Point> pointIterator = horizontalStackBarChartEl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator =
                    horizontalStackBarChartEl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            horizontalStackBarChartView.addData(dataset);
            if (!isFieldEmpty(horizontalStackBarChartEl.getAxisColor()))
                horizontalStackBarChartView.setAxisColor(
                        Color.parseColor(horizontalStackBarChartEl.getAxisColor()));
            if (!isFieldEmpty(horizontalStackBarChartEl.getLabelsColor()))
                horizontalStackBarChartView.setLabelsColor(
                        Color.parseColor(horizontalStackBarChartEl.getLabelsColor()));
            horizontalStackBarChartView.show();
            result = horizontalStackBarChartView;
        } else if (element instanceof Elements.ScrollerEl) {
            Elements.ScrollerEl scrollerEl = (Elements.ScrollerEl) element;
            ScrollView scrollView = new ScrollView(context);
            View panelView = render(Elements.PanelEl.LayoutType.NONE, scrollerEl.getPanel());
            panelView.setLayoutParams(new ScrollView.LayoutParams(
                    ScrollView.LayoutParams.MATCH_PARENT,
                    ScrollView.LayoutParams.WRAP_CONTENT));
            scrollView.addView(panelView);
            result = scrollView;
        }

        if (result != null) {
            result = fillGeneralProps(result, parentLayoutType, element);
        }

        return result;
    }

    private View fillGeneralProps(View view, Elements.PanelEl.LayoutType parentLayoutType, Elements.Element el) {
        if (!(el.getPaddingLeft() == 0 && el.getPaddingTop() == 0 &&
                el.getPaddingTop() == 0 && el.getPaddingBottom() == 0))
            view.setPadding(
                    GraphicsHelper.dpToPx(el.getPaddingLeft()),
                    GraphicsHelper.dpToPx(el.getPaddingTop()),
                    GraphicsHelper.dpToPx(el.getPaddingRight()),
                    GraphicsHelper.dpToPx(el.getPaddingBottom()));
        if (!isFieldEmpty(el.getBorderColor())) {
            CardView outerCV = new CardView(context);
            outerCV.setCardBackgroundColor(Color.parseColor(el.getBorderColor()));
            CardView innerCV = new CardView(context);
            if (!isFieldEmpty(el.getBackColor()))
                innerCV.setCardBackgroundColor(Color.parseColor(el.getBackColor()));
            outerCV.addView(innerCV);
            CardView.LayoutParams cvlp = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.MATCH_PARENT);
            int bw = GraphicsHelper.dpToPx(1);
            if (!isFieldEmpty(el.getBorderWidth()))
                bw = GraphicsHelper.dpToPx(el.getBorderWidth());
            cvlp.setMargins(bw, bw, bw, bw);
            innerCV.setLayoutParams(cvlp);
            innerCV.addView(view);
            cvlp = new CardView.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(cvlp);
            view = outerCV;
        } else {
            if (!isFieldEmpty(el.getBackColor())) {
                if (view instanceof CardView)
                    ((CardView) view).setCardBackgroundColor(Color.parseColor(el.getBackColor()));
                else
                    view.setBackgroundColor(Color.parseColor(el.getBackColor()));
            }
        }

        if (!isFieldEmpty(el.getCornerRadius())) {
            if (!(view instanceof CardView)) {
                CardView cardView = new CardView(context);
                cardView.addView(view);
                CardView.LayoutParams cvlp = new CardView.LayoutParams(
                        CardView.LayoutParams.MATCH_PARENT,
                        CardView.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(cvlp);
                view = cardView;
            } else {
                if (((CardView) view).getChildAt(0) instanceof CardView) {
                    ((CardView)((CardView) view).getChildAt(0))
                            .setRadius(GraphicsHelper.dpToPx(el.getCornerRadius()));
                }
            }
            ((CardView)view).setRadius(GraphicsHelper.dpToPx(el.getCornerRadius()));
        }
        if (view instanceof CardView)
            ((CardView) view).setCardElevation(el.getElevation());
        else
            view.setElevation(el.getElevation());
        ViewGroup.LayoutParams mlp = null;
        if (parentLayoutType == Elements.PanelEl.LayoutType.RELATIVE) {
            mlp = new RelativeLayout.LayoutParams(0, 0);
            ((RelativeLayout.LayoutParams) mlp).setMargins(
                    GraphicsHelper.dpToPx(el.getMarginLeft()),
                    GraphicsHelper.dpToPx(el.getMarginTop()),
                    GraphicsHelper.dpToPx(el.getMarginRight()),
                    GraphicsHelper.dpToPx(el.getMarginBottom()));
        } else if (parentLayoutType == Elements.PanelEl.LayoutType.LINEAR_VERTICAL ||
                parentLayoutType == Elements.PanelEl.LayoutType.LINEAR_HORIZONTAL) {
            mlp = new LinearLayout.LayoutParams(0, 0);
            ((LinearLayout.LayoutParams) mlp).setMargins(
                    GraphicsHelper.dpToPx(el.getMarginLeft()),
                    GraphicsHelper.dpToPx(el.getMarginTop()),
                    GraphicsHelper.dpToPx(el.getMarginRight()),
                    GraphicsHelper.dpToPx(el.getMarginBottom()));
        }

        if (mlp != null) {

            if (!isFieldEmpty(el.getWidth())) {
                if (el.getWidth() == Elements.Element.MATCH_PARENT)
                    mlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                else if (el.getWidth() == Elements.Element.WRAP_CONTENT)
                    mlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                else
                    mlp.width = GraphicsHelper.dpToPx(el.getWidth());
            }
            else
                mlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (!isFieldEmpty(el.getHeight())) {
                if (el.getHeight() == Elements.Element.MATCH_PARENT)
                    mlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                else if (el.getHeight() == Elements.Element.WRAP_CONTENT)
                    mlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else
                    mlp.height = GraphicsHelper.dpToPx(el.getHeight());
            }
            else
                mlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (el.getX() == Elements.Element.CENTER) {
                if (mlp instanceof RelativeLayout.LayoutParams)
                    ((RelativeLayout.LayoutParams) mlp).addRule(RelativeLayout.CENTER_HORIZONTAL);
                else if (mlp instanceof LinearLayout.LayoutParams)
                    ((LinearLayout.LayoutParams) mlp).gravity = Gravity.CENTER_HORIZONTAL;
            } else
                view.setX(GraphicsHelper.dpToPx(el.getX()));

            if (el.getY() == Elements.Element.CENTER) {
                if (mlp instanceof RelativeLayout.LayoutParams)
                    ((RelativeLayout.LayoutParams) mlp).addRule(RelativeLayout.CENTER_VERTICAL);
                else if (mlp instanceof LinearLayout.LayoutParams) {
                    if (((LinearLayout.LayoutParams) mlp).gravity == Gravity.CENTER_HORIZONTAL)
                        ((LinearLayout.LayoutParams) mlp).gravity = Gravity.CENTER;
                    else
                        ((LinearLayout.LayoutParams) mlp).gravity = Gravity.CENTER_VERTICAL;
                }
            } else
                view.setY(GraphicsHelper.dpToPx(el.getY()));

            view.setLayoutParams(mlp);
        }


        view.setRotationX(el.getRotationX());
        view.setRotationY(el.getRotationY());
        view.setRotation(el.getRotation());

        return view;
    }

    private boolean isFieldEmpty(String input) {
        return input == null || input.length() == 0;
    }

    private boolean isFieldEmpty(int input) {
        return input == 0;
    }

    private boolean isFieldEmpty(long input) {
        return input == 0;
    }

    private boolean isFieldEmpty(float input) {
        return input == 0;
    }

    private boolean isFieldEmpty(double input) {
        return input == 0;
    }

    private boolean isFieldEmpty(Elements.ImageEl.ImageScaleType input) {
        return input == Elements.ImageEl.ImageScaleType.NONE;
    }

    private boolean isFieldEmpty(Elements.TextEl.GravityType input) {
        return input == Elements.TextEl.GravityType.NONE;
    }

    private boolean isFieldEmpty(List input) {
        return input == null || input.size() == 0;
    }
}

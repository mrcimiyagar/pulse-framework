package kasper.android.pulseframework.engines;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.CompoundButtonCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.moos.library.CircleProgressView;
import com.moos.library.HorizontalProgressView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kasper.android.pulseframework.components.RoundRectViewEdited;
import kasper.android.pulseframework.models.Data;
import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Tuple;
import kasper.android.pulseframework.utils.GraphicsHelper;
import tcking.github.com.giraffeplayer2.VideoView;

public class UiInitiatorEngine {

    private Context context;
    private String appName;

    public UiInitiatorEngine(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }

    public Tuple<View, List<Pair<Controls.Control, View>>
            , Hashtable<String, Pair<Controls.Control, View>>> buildViewTree(
            Controls.PanelCtrl.LayoutType parentLayoutType, Controls.Control control) {
        View result = null;
        Hashtable<String, Pair<Controls.Control, View>> idTable = new Hashtable<>();
        List<Pair<Controls.Control, View>> statefulCtrls = new ArrayList<>();
        if (control instanceof Controls.PanelCtrl) {
            Controls.PanelCtrl panelCtrl = (Controls.PanelCtrl) control;
            if (!isFieldEmpty(panelCtrl.getLayoutType())) {
                if (panelCtrl.getLayoutType() == Controls.PanelCtrl.LayoutType.RELATIVE) {
                    RelativeLayout relativeLayout = new RelativeLayout(context);
                    for (Controls.Control el : panelCtrl.getControls()) {
                        Tuple<View, List<Pair<Controls.Control, View>>
                                , Hashtable<String, Pair<Controls.Control, View>>> pair =
                                buildViewTree(panelCtrl.getLayoutType(), el);
                        View childView = pair.getItem1();
                        statefulCtrls.addAll(pair.getItem2());
                        idTable.putAll(pair.getItem3());
                        relativeLayout.addView(childView);
                    }
                    result = relativeLayout;
                } else if (panelCtrl.getLayoutType() == Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    for (Controls.Control el : panelCtrl.getControls()) {
                        Tuple<View, List<Pair<Controls.Control, View>>
                                , Hashtable<String, Pair<Controls.Control, View>>> pair =
                                buildViewTree(panelCtrl.getLayoutType(), el);
                        View childView = pair.getItem1();
                        statefulCtrls.addAll(pair.getItem2());
                        idTable.putAll(pair.getItem3());
                        linearLayout.addView(childView);
                    }
                    result = linearLayout;
                } else if (panelCtrl.getLayoutType() == Controls.PanelCtrl.LayoutType.LINEAR_HORIZONTAL) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    for (Controls.Control el : panelCtrl.getControls()) {
                        Tuple<View, List<Pair<Controls.Control, View>>
                                , Hashtable<String, Pair<Controls.Control, View>>> pair =
                                buildViewTree(panelCtrl.getLayoutType(), el);
                        View childView = pair.getItem1();
                        statefulCtrls.addAll(pair.getItem2());
                        idTable.putAll(pair.getItem3());
                        linearLayout.addView(childView);
                    }
                    result = linearLayout;
                }
            } else {
                RelativeLayout relativeLayout = new RelativeLayout(context);
                for (Controls.Control el : panelCtrl.getControls()) {
                    Tuple<View, List<Pair<Controls.Control, View>>
                            , Hashtable<String, Pair<Controls.Control, View>>> pair =
                            buildViewTree(panelCtrl.getLayoutType(), el);
                    View childView = pair.getItem1();
                    statefulCtrls.addAll(pair.getItem2());
                    idTable.putAll(pair.getItem3());
                    relativeLayout.addView(childView);
                }
                result = relativeLayout;
            }
        } else if (control instanceof Controls.TextCtrl) {
            Controls.TextCtrl textCtrl = (Controls.TextCtrl) control;
            TextView textView = new TextView(context);
            if (!isFieldEmpty(textCtrl.getText()))
                textView.setText(textCtrl.getText());
            if (!isFieldEmpty(textCtrl.getTextSize()))
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textCtrl.getTextSize());
            if (!isFieldEmpty(textCtrl.getTextColor()))
                textView.setTextColor(Color.parseColor(textCtrl.getTextColor()));
            if (!isFieldEmpty(textCtrl.getGravityType())) {
                if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.LEFT) {
                    textView.setGravity(Gravity.LEFT);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.TOP) {
                    textView.setGravity(Gravity.TOP);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.RIGHT) {
                    textView.setGravity(Gravity.RIGHT);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.BOTTOM) {
                    textView.setGravity(Gravity.BOTTOM);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.CENTER_VERTICAL) {
                    textView.setGravity(Gravity.CENTER_VERTICAL);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.CENTER_HORIZONTAL) {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                } else if (textCtrl.getGravityType() == Controls.TextCtrl.GravityType.CENTER) {
                    textView.setGravity(Gravity.CENTER);
                }
            }
            result = textView;
        } else if (control instanceof Controls.ImageCtrl) {
            Controls.ImageCtrl imageCtrl = (Controls.ImageCtrl) control;
            ImageView imageView = new ImageView(context);
            if (!isFieldEmpty(imageCtrl.getScaleType()))
                imageView.setScaleType(ImageView.ScaleType.valueOf(
                        ImageView.ScaleType.class,
                        imageCtrl.getScaleType().toString()));
            if (!isFieldEmpty(imageCtrl.getImageUrl()))
                Glide.with(context)
                        .load(imageCtrl.getImageUrl())
                        .into(imageView);
            result = imageView;
        } else if (control instanceof Controls.InputFieldCtrl) {
            Controls.InputFieldCtrl inputFieldCtrl = (Controls.InputFieldCtrl) control;
            EditText editText = new EditText(context);
            if (!isFieldEmpty(inputFieldCtrl.getHint()))
                editText.setHint(inputFieldCtrl.getHint());
            if (!isFieldEmpty(inputFieldCtrl.getHintColor()))
                editText.setHintTextColor(Color.parseColor(inputFieldCtrl.getHintColor()));
            else
                editText.setHintTextColor(Color.GRAY);
            if (!isFieldEmpty(inputFieldCtrl.getTextColor()))
                editText.setTextColor(Color.parseColor(inputFieldCtrl.getTextColor()));
            else
                editText.setTextColor(Color.BLACK);
            if (!isFieldEmpty(inputFieldCtrl.getTextSize()))
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, inputFieldCtrl.getTextSize());
            else
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (!isFieldEmpty(inputFieldCtrl.getLineColor()))
                editText.getBackground().setColorFilter(
                        Color.parseColor(inputFieldCtrl.getLineColor()), PorterDuff.Mode.SRC_ATOP);
            if (!isFieldEmpty(inputFieldCtrl.getText()))
                editText.setText(inputFieldCtrl.getText());
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    inputFieldCtrl.setText(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            result = editText;
        } else if (control instanceof Controls.ButtonCtrl) {
            Controls.ButtonCtrl buttonCtrl = (Controls.ButtonCtrl) control;
            Button button = new Button(context);
            if (!isFieldEmpty(buttonCtrl.getCaption()))
                button.setText(buttonCtrl.getCaption());
            if (!isFieldEmpty(buttonCtrl.getCaptionSize()))
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonCtrl.getCaptionSize());
            else
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (!isFieldEmpty(buttonCtrl.getCaptionColor()))
                button.setTextColor(Color.parseColor(buttonCtrl.getCaptionColor()));
            else
                button.setTextColor(Color.BLACK);
            result = button;
        } else if (control instanceof Controls.ProgressCtrl) {
            Controls.ProgressCtrl progressCtrl = (Controls.ProgressCtrl) control;
            if (progressCtrl.getProgressType() == Controls.ProgressCtrl.ProgressType.CIRCULAR) {
                CircleProgressView circleProgressView = new CircleProgressView(context);
                circleProgressView.setCircleBroken(progressCtrl.isCircleBroken());
                circleProgressView.setTrackWidth(progressCtrl.getTrackWidth());
                circleProgressView.setFillEnabled(progressCtrl.isFillEnabled());
                if (!isFieldEmpty(progressCtrl.getProgressTextSize()))
                    circleProgressView.setProgressTextSize(progressCtrl.getProgressTextSize());
                if (!isFieldEmpty(progressCtrl.getProgressTextColor()))
                    circleProgressView.setProgressTextColor(
                            Color.parseColor(progressCtrl.getProgressTextColor()));
                circleProgressView.setTrackEnabled(progressCtrl.isTrackEnabled());
                if (!isFieldEmpty(progressCtrl.getTrackColor()))
                    circleProgressView.setTrackColor(Color.parseColor(progressCtrl.getTrackColor()));
                circleProgressView.setProgressTextVisibility(progressCtrl.isProgressTextVisibility());
                circleProgressView.setGraduatedEnabled(progressCtrl.isGraduatedEnabled());
                circleProgressView.setScaleZoneWidth(
                        GraphicsHelper.dpToPx(progressCtrl.getScaleZoneWidth()));
                circleProgressView.setScaleZoneLength(
                        GraphicsHelper.dpToPx(progressCtrl.getScaleZoneLength()));
                circleProgressView.setScaleZonePadding(
                        GraphicsHelper.dpToPx(progressCtrl.getScaleZonePadding()));
                circleProgressView.setScaleZoneCornerRadius(
                        GraphicsHelper.dpToPx(progressCtrl.getScaleZoneCornerRadius()));
                result = circleProgressView;
            } else if (progressCtrl.getProgressType() == Controls.ProgressCtrl.ProgressType.HORIZONTAL) {
                HorizontalProgressView horizontalProgressView = new HorizontalProgressView(context);
                if (!isFieldEmpty(progressCtrl.getTrackWidth()))
                    horizontalProgressView.setTrackWidth(progressCtrl.getTrackWidth());
                if (!isFieldEmpty(progressCtrl.getProgressTextSize()))
                    horizontalProgressView.setProgressTextSize(progressCtrl.getProgressTextSize());
                if (!isFieldEmpty(progressCtrl.getProgressTextColor()))
                    horizontalProgressView.setProgressTextColor(
                            Color.parseColor(progressCtrl.getProgressTextColor()));
                horizontalProgressView.setTrackEnabled(progressCtrl.isTrackEnabled());
                if (!isFieldEmpty(progressCtrl.getTrackColor()))
                    horizontalProgressView.setTrackColor(Color.parseColor(progressCtrl.getTrackColor()));
                horizontalProgressView.setProgressTextVisibility(progressCtrl.isProgressTextVisibility());
                horizontalProgressView.setProgressCornerRadius(
                        GraphicsHelper.dpToPx(progressCtrl.getProgressCornerRadius()));
                if (!isFieldEmpty(progressCtrl.getProgressTextPaddingBottom()))
                    horizontalProgressView.setProgressTextPaddingBottom(
                            GraphicsHelper.dpToPx(progressCtrl.getProgressTextPaddingBottom()));
                horizontalProgressView.setProgressTextMoved(progressCtrl.isProgressTextMoved());
                result = horizontalProgressView;
            }
        } else if (control instanceof Controls.VideoPlayerCtrl) {
            Controls.VideoPlayerCtrl videoPlayerCtrl = (Controls.VideoPlayerCtrl) control;
            VideoView videoView = new VideoView(context);
            videoView.getVideoInfo().setPortraitWhenFullScreen(true);
            videoView.setVideoPath(Uri.parse(videoPlayerCtrl
                    .getVideoUrl()).toString());
            if (!isFieldEmpty(videoPlayerCtrl.getProgress()))
                videoView.getPlayer().seekTo(videoPlayerCtrl.getProgress());
            if (videoPlayerCtrl.isPlaying())
                videoView.getPlayer().start();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    videoPlayerCtrl.setProgress(videoView.getPlayer().getCurrentPosition());
                }
            }, 1, 1000);
            result = videoView;
        } else if (control instanceof Controls.LineChartCtrl) {
            Controls.LineChartCtrl chartEl = (Controls.LineChartCtrl) control;
            LineChartView lineChartView = new LineChartView(context);
            LineSet dataset = initLineChartView((Controls.LineChartCtrl) control);
            lineChartView.addData(dataset);
            if (!isFieldEmpty(chartEl.getAxisColor()))
                lineChartView.setAxisColor(Color.parseColor(chartEl.getAxisColor()));
            if (!isFieldEmpty(chartEl.getLabelsColor()))
                lineChartView.setLabelsColor(Color.parseColor(chartEl.getLabelsColor()));
            lineChartView.setClickablePointRadius(GraphicsHelper.dpToPx(chartEl.getDotsRadius()));
            lineChartView.show();
            result = lineChartView;
        } else if (control instanceof Controls.BarChartCtrl) {
            Controls.BarChartCtrl chartEl = (Controls.BarChartCtrl) control;
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
        } else if (control instanceof Controls.HorizontalBarChartCtrl) {
            Controls.HorizontalBarChartCtrl chartEl = (Controls.HorizontalBarChartCtrl) control;
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
        } else if (control instanceof Controls.StackBarChartCtrl) {
            Controls.StackBarChartCtrl stackBarChartCtrl = (Controls.StackBarChartCtrl) control;
            StackBarChartView stackBarChartView = new StackBarChartView(context);
            if (!isFieldEmpty(stackBarChartCtrl.getBarSpacing()))
                stackBarChartView.setBarSpacing(GraphicsHelper.dpToPx(stackBarChartCtrl.getBarSpacing()));
            if (!isFieldEmpty(stackBarChartCtrl.getBarBackgroundColor()))
                stackBarChartView.setBarBackgroundColor(
                        Color.parseColor(stackBarChartCtrl.getBarBackgroundColor()));
            if (!isFieldEmpty(stackBarChartCtrl.getRoundCorners()))
                stackBarChartView.setRoundCorners(
                        GraphicsHelper.dpToPx(stackBarChartCtrl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(stackBarChartCtrl.getBarsColor()))
                dataset.setColor(Color.parseColor(stackBarChartCtrl.getBarsColor()));
            Iterator<Data.Point> pointIterator = stackBarChartCtrl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator = stackBarChartCtrl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            stackBarChartView.addData(dataset);
            if (!isFieldEmpty(stackBarChartCtrl.getAxisColor()))
                stackBarChartView.setAxisColor(Color.parseColor(stackBarChartCtrl.getAxisColor()));
            if (!isFieldEmpty(stackBarChartCtrl.getLabelsColor()))
                stackBarChartView.setLabelsColor(Color.parseColor(stackBarChartCtrl.getLabelsColor()));
            stackBarChartView.show();
            result = stackBarChartView;
        } else if (control instanceof Controls.HorizontalStackBarChartCtrl) {
            Controls.HorizontalStackBarChartCtrl horizontalStackBarChartCtrl =
                    (Controls.HorizontalStackBarChartCtrl) control;
            HorizontalStackBarChartView horizontalStackBarChartView =
                    new HorizontalStackBarChartView(context);
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getBarSpacing()))
                horizontalStackBarChartView.setBarSpacing(
                        GraphicsHelper.dpToPx(horizontalStackBarChartCtrl.getBarSpacing()));
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getBarBackgroundColor()))
                horizontalStackBarChartView.setBarBackgroundColor(
                        Color.parseColor(horizontalStackBarChartCtrl.getBarBackgroundColor()));
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getRoundCorners()))
                horizontalStackBarChartView.setRoundCorners(
                        GraphicsHelper.dpToPx(horizontalStackBarChartCtrl.getRoundCorners()));
            BarSet dataset = new BarSet();
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getBarsColor()))
                dataset.setColor(Color.parseColor(horizontalStackBarChartCtrl.getBarsColor()));
            Iterator<Data.Point> pointIterator = horizontalStackBarChartCtrl.getPoints().iterator();
            Iterator<Data.StringValue> barColorIterator =
                    horizontalStackBarChartCtrl.getBarColors().iterator();
            while (pointIterator.hasNext() && barColorIterator.hasNext()) {
                Data.Point point = pointIterator.next();
                Data.StringValue barColor = barColorIterator.next();
                Bar bar = new Bar(point.getLabel(), point.getValue());
                bar.setColor(Color.parseColor(barColor.getValue()));
                dataset.addBar(bar);
            }
            horizontalStackBarChartView.addData(dataset);
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getAxisColor()))
                horizontalStackBarChartView.setAxisColor(
                        Color.parseColor(horizontalStackBarChartCtrl.getAxisColor()));
            if (!isFieldEmpty(horizontalStackBarChartCtrl.getLabelsColor()))
                horizontalStackBarChartView.setLabelsColor(
                        Color.parseColor(horizontalStackBarChartCtrl.getLabelsColor()));
            horizontalStackBarChartView.show();
            result = horizontalStackBarChartView;
        } else if (control instanceof Controls.ScrollerCtrl) {
            Controls.ScrollerCtrl scrollerCtrl = (Controls.ScrollerCtrl) control;
            ScrollView scrollView = new ScrollView(context);
            Tuple<View, List<Pair<Controls.Control, View>>
                    , Hashtable<String, Pair<Controls.Control, View>>> pair =
                    buildViewTree(Controls.PanelCtrl.LayoutType.RELATIVE, scrollerCtrl.getPanel());
            View panelView = pair.getItem1();
            statefulCtrls.addAll(pair.getItem2());
            idTable.putAll(pair.getItem3());
            panelView.setLayoutParams(new ScrollView.LayoutParams(
                    ScrollView.LayoutParams.MATCH_PARENT,
                    ScrollView.LayoutParams.WRAP_CONTENT));
            scrollView.addView(panelView);
            scrollView.scrollTo(0, scrollerCtrl.getPosition());
            scrollView.getViewTreeObserver()
                    .addOnScrollChangedListener(() -> {
                        scrollerCtrl.setPosition(scrollView.getScrollY());
                    });
            result = scrollView;
        } else if (control instanceof Controls.CheckCtrl) {
            Controls.CheckCtrl checkCtrl = (Controls.CheckCtrl) control;
            CheckBox checkBox = new CheckBox(context);
            if (!isFieldEmpty(checkCtrl.getCaption()))
                checkBox.setText(checkCtrl.getCaption());
            if (!isFieldEmpty(checkCtrl.getCaptionColor()))
                checkBox.setTextColor(Color.parseColor(checkCtrl.getCaptionColor()));
            if (!isFieldEmpty(checkCtrl.getCaptionSize()))
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, checkCtrl.getCaptionSize());
            if (!isFieldEmpty(checkCtrl.getTintColor())) {
                ColorStateList  colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_checked}, // unchecked
                                new int[]{android.R.attr.state_checked} , // checked
                        },
                        new int[]{
                                Color.parseColor(checkCtrl.getTintColor()),
                                Color.parseColor(checkCtrl.getTintColor()),
                        }
                );
                CompoundButtonCompat.setButtonTintList(checkBox,colorStateList);
            }
            checkBox.setChecked(checkCtrl.isChecked());
            checkBox.setOnCheckedChangeListener((compoundButton, b)
                    -> checkCtrl.setChecked(checkBox.isChecked()));
            result = checkBox;
        } else if (control instanceof Controls.OptionCtrl) {
            Controls.OptionCtrl optionCtrl = (Controls.OptionCtrl) control;
            RadioButton radioButton = new RadioButton(context);
            if (!isFieldEmpty(optionCtrl.getCaption()))
                radioButton.setText(optionCtrl.getCaption());
            if (!isFieldEmpty(optionCtrl.getCaptionColor()))
                radioButton.setTextColor(Color.parseColor(optionCtrl.getCaptionColor()));
            if (!isFieldEmpty(optionCtrl.getCaptionSize()))
                radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, optionCtrl.getCaptionSize());
            if (!isFieldEmpty(optionCtrl.getTintColor())) {
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{-android.R.attr.state_checked}, // unchecked
                                new int[]{android.R.attr.state_checked}, // checked
                        },
                        new int[]{
                                Color.parseColor(optionCtrl.getTintColor()),
                                Color.parseColor(optionCtrl.getTintColor()),
                        }
                );
                CompoundButtonCompat.setButtonTintList(radioButton, colorStateList);
            }
            radioButton.setChecked(optionCtrl.isChecked());
            radioButton.setOnCheckedChangeListener((compoundButton, b)
                    -> optionCtrl.setChecked(radioButton.isChecked()));
            result = radioButton;
        } else if (control instanceof Controls.DropDownCtrl) {
            Controls.DropDownCtrl dropDownCtrl = (Controls.DropDownCtrl) control;
            Spinner spinner = new Spinner(context);
            spinner.setAdapter(new SpinnerAdapter() {
                @Override
                public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                    Tuple<View, List<Pair<Controls.Control, View>>,
                            Hashtable<String, Pair<Controls.Control, View>>> pair = buildViewTree(
                            Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL,
                            dropDownCtrl.getItems().get(i));
                    View itemView = pair.getItem1();
                    initFingerprint(pair.getItem2(), i);
                    idTable.putAll(pair.getItem3());
                    return itemView;
                }

                @Override
                public void registerDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public int getCount() {
                    return dropDownCtrl.getItems().size();
                }

                @Override
                public Object getItem(int i) {
                    return dropDownCtrl.getItems().get(i);
                }

                @Override
                public long getItemId(int i) {
                    return i;
                }

                @Override
                public boolean hasStableIds() {
                    return true;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    Tuple<View, List<Pair<Controls.Control, View>>,
                            Hashtable<String, Pair<Controls.Control, View>>> pair = buildViewTree(
                            Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL,
                            dropDownCtrl.getItems().get(i));
                    View itemView = pair.getItem1();
                    initFingerprint(pair.getItem2(), i);
                    idTable.putAll(pair.getItem3());
                    return itemView;
                }

                @Override
                public int getItemViewType(int i) {
                    return 1;
                }

                @Override
                public int getViewTypeCount() {
                    return 1;
                }

                @Override
                public boolean isEmpty() {
                    return dropDownCtrl.getItems().size() == 0;
                }
            });
            spinner.setSelection(dropDownCtrl.getSelectedPos());
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    dropDownCtrl.setSelectedPos(spinner.getSelectedItemPosition());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            result = spinner;
        } else if (control instanceof Controls.RecyclerListCtrl) {
            Controls.RecyclerListCtrl recyclerListCtrl = (Controls.RecyclerListCtrl) control;
            RecyclerView recyclerView = new RecyclerView(context);
            if (recyclerListCtrl.getRecyclerType() ==
                    Controls.RecyclerListCtrl.RecyclerLayoutType.LINEAR) {
                if (recyclerListCtrl.getOrientation() ==
                        Controls.RecyclerListCtrl.RecyclerOrientation.HORIZONTAL)
                    recyclerView.setLayoutManager(new LinearLayoutManager(
                            context, RecyclerView.HORIZONTAL, false));
                else if (recyclerListCtrl.getOrientation() ==
                        Controls.RecyclerListCtrl.RecyclerOrientation.VERTICAL)
                    recyclerView.setLayoutManager(new LinearLayoutManager(
                            context, RecyclerView.VERTICAL, false));
            } else {
                if (recyclerListCtrl.getOrientation() ==
                        Controls.RecyclerListCtrl.RecyclerOrientation.HORIZONTAL)
                    recyclerView.setLayoutManager(new GridLayoutManager(
                            context, recyclerListCtrl.getGridSpanCount()
                            , RecyclerView.HORIZONTAL, false));
                else if (recyclerListCtrl.getOrientation() ==
                        Controls.RecyclerListCtrl.RecyclerOrientation.VERTICAL)
                    recyclerView.setLayoutManager(new GridLayoutManager(
                            context, recyclerListCtrl.getGridSpanCount()
                            , RecyclerView.VERTICAL, false));
            }
            recyclerView.setAdapter(new RecyclerView.Adapter() {
                @NonNull
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    RelativeLayout itemContainer = new RelativeLayout(context);
                    itemContainer.setLayoutParams(
                            new RecyclerView.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                    return new ItemHolder(itemContainer);
                }

                @Override
                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
                    Tuple<View, List<Pair<Controls.Control, View>>,
                            Hashtable<String, Pair<Controls.Control, View>>> pair =
                            buildViewTree(
                                    Controls.PanelCtrl.LayoutType.RELATIVE,
                                    recyclerListCtrl.getItems().get(i));
                    View itemView = pair.getItem1();
                    initFingerprint(pair.getItem2(), i);
                    idTable.putAll(pair.getItem3());
                    ((ViewGroup)((ItemHolder) holder).itemView).addView(itemView);
                }

                @Override
                public int getItemCount() {
                    return recyclerListCtrl.getItems().size();
                }

                class ItemHolder extends RecyclerView.ViewHolder {
                    ItemHolder(View itemView) {
                        super(itemView);
                    }
                }
            });
            result = recyclerView;
        }

        if (result != null) {
            result = fillGeneralProps(result, parentLayoutType, control);
        }

        if (!isFieldEmpty(control.getId()))
            idTable.put(control.getId(), new Pair<>(control, result));

        return new Tuple<>(result, statefulCtrls, idTable);
    }

    private void initFingerprint(List<Pair<Controls.Control, View>> list, int i) {
        for (Pair<Controls.Control, View> statefulCtrl : list) {
            View target = statefulCtrl.second;
            if (target instanceof CardView) {
                target = ((CardView) target).getChildAt(0);
                if (target instanceof CardView) {
                    target = ((CardView) target).getChildAt(0);
                }
            }
            if (statefulCtrl.first instanceof Controls.VideoPlayerCtrl) {
                VideoView videoView = (VideoView) target;
                videoView.setFingerprint(i);
            }
        }
    }

    private View fillGeneralProps(View view, Controls.PanelCtrl.LayoutType parentLayoutType, Controls.Control el) {
        if (!(el.getPaddingLeft() == 0 && el.getPaddingTop() == 0 &&
                el.getPaddingTop() == 0 && el.getPaddingBottom() == 0))
            view.setPadding(
                    GraphicsHelper.dpToPx(el.getPaddingLeft()),
                    GraphicsHelper.dpToPx(el.getPaddingTop()),
                    GraphicsHelper.dpToPx(el.getPaddingRight()),
                    GraphicsHelper.dpToPx(el.getPaddingBottom()));

        if (!isFieldEmpty(el.getBackColor()))
            view.setBackgroundColor(Color.parseColor(el.getBackColor()));

        RoundRectViewEdited roundRectView = new RoundRectViewEdited(context, el.isNoShadow());
        if (!isFieldEmpty(el.getBorderColor()))
            roundRectView.setBorderColor(Color.parseColor(el.getBorderColor()));
        roundRectView.setBorderWidth(GraphicsHelper.dpToPx(el.getBorderWidth()));
        roundRectView.setTopLeftRadius(GraphicsHelper.dpToPx(el.getTopLeftRadius()));
        roundRectView.setTopRightRadius(GraphicsHelper.dpToPx(el.getTopRightRadius()));
        roundRectView.setBottomLeftRadius(GraphicsHelper.dpToPx(el.getBottomLeftRadius()));
        roundRectView.setBottomRightRadius(GraphicsHelper.dpToPx(el.getBottomRightRadius()));
        if (isFieldEmpty(el.getBackColor()))
            roundRectView.setBackgroundColor(Color.TRANSPARENT);
        roundRectView.addView(view);
        view = roundRectView;

        view.setElevation(el.getElevation());

        ViewGroup.LayoutParams mlp = null;
        if (parentLayoutType == Controls.PanelCtrl.LayoutType.RELATIVE) {
            mlp = new RelativeLayout.LayoutParams(0, 0);
            ((RelativeLayout.LayoutParams) mlp).setMargins(
                    GraphicsHelper.dpToPx(el.getMarginLeft()),
                    GraphicsHelper.dpToPx(el.getMarginTop()),
                    GraphicsHelper.dpToPx(el.getMarginRight()),
                    GraphicsHelper.dpToPx(el.getMarginBottom()));
        } else if (parentLayoutType == Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL ||
                parentLayoutType == Controls.PanelCtrl.LayoutType.LINEAR_HORIZONTAL) {
            mlp = new LinearLayout.LayoutParams(0, 0);
            ((LinearLayout.LayoutParams) mlp).setMargins(
                    GraphicsHelper.dpToPx(el.getMarginLeft()),
                    GraphicsHelper.dpToPx(el.getMarginTop()),
                    GraphicsHelper.dpToPx(el.getMarginRight()),
                    GraphicsHelper.dpToPx(el.getMarginBottom()));
        }

        if (mlp != null) {

            if (!isFieldEmpty(el.getWidth())) {
                if (el.getWidth() == Controls.Control.MATCH_PARENT)
                    mlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                else if (el.getWidth() == Controls.Control.WRAP_CONTENT)
                    mlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                else
                    mlp.width = GraphicsHelper.dpToPx(el.getWidth());
            }
            else
                mlp.width = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (!isFieldEmpty(el.getHeight())) {
                if (el.getHeight() == Controls.Control.MATCH_PARENT)
                    mlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                else if (el.getHeight() == Controls.Control.WRAP_CONTENT)
                    mlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else
                    mlp.height = GraphicsHelper.dpToPx(el.getHeight());
            }
            else
                mlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            if (el.getX() == Controls.Control.CENTER) {
                if (mlp instanceof RelativeLayout.LayoutParams)
                    ((RelativeLayout.LayoutParams) mlp).addRule(RelativeLayout.CENTER_HORIZONTAL);
                else if (mlp instanceof LinearLayout.LayoutParams)
                    ((LinearLayout.LayoutParams) mlp).gravity = Gravity.CENTER_HORIZONTAL;
            } else
                view.setX(GraphicsHelper.dpToPx(el.getX()));

            if (el.getY() == Controls.Control.CENTER) {
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

    LineSet initLineChartView(Controls.LineChartCtrl chartEl) {
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
        return dataset;
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

    private boolean isFieldEmpty(Controls.PanelCtrl.LayoutType input) {
        return input == null;
    }

    private boolean isFieldEmpty(Controls.ImageCtrl.ImageScaleType input) {
        return input == null || input == Controls.ImageCtrl.ImageScaleType.NONE;
    }

    private boolean isFieldEmpty(Controls.TextCtrl.GravityType input) {
        return input == null || input == Controls.TextCtrl.GravityType.NONE;
    }

    private boolean isFieldEmpty(List input) {
        return input == null || input.size() == 0;
    }
}

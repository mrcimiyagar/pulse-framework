package kasper.android.pulseframework.engines;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.moos.library.CircleProgressView;
import com.moos.library.HorizontalProgressView;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Data;
import kasper.android.pulseframework.models.Tuple;
import kasper.android.pulseframework.models.Updates;
import kasper.android.pulseframework.utils.GraphicsHelper;
import tcking.github.com.giraffeplayer2.VideoView;

public class UiUpdaterEngine {

    private static final String TAG = "KasperPulseFramework";

    private Context context;
    private String appName;

    public UiUpdaterEngine(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }

    @SuppressLint("RtlHardcoded")
    public void updateUi(Hashtable<String, Pair<Controls.Control, View>> idTable, Updates.Update update) {
        Pair<Controls.Control, View> pair = idTable.get(update.getControlId());
        if (pair != null) {
            Controls.Control control = pair.first;
            View view = pair.second;
            if (update instanceof Updates.ControlUpdateWidth) {
                control.setWidth(((Updates.ControlUpdateWidth) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (((Updates.ControlUpdateWidth) update).getValue() != Controls.Control.MATCH_PARENT
                        && ((Updates.ControlUpdateWidth) update).getValue() != Controls.Control.WRAP_CONTENT)
                    params.width = GraphicsHelper.dpToPx(((Updates.ControlUpdateWidth) update).getValue());
                else
                    params.width = ((Updates.ControlUpdateWidth) update).getValue();
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdateHeight) {
                control.setWidth(((Updates.ControlUpdateHeight) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (((Updates.ControlUpdateHeight) update).getValue() != Controls.Control.MATCH_PARENT
                        && ((Updates.ControlUpdateHeight) update).getValue() != Controls.Control.WRAP_CONTENT)
                    params.height = GraphicsHelper.dpToPx(((Updates.ControlUpdateHeight) update).getValue());
                else
                    params.height = ((Updates.ControlUpdateHeight) update).getValue();
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdateX) {
                control.setX(((Updates.ControlUpdateX) update).getValue());
                if (((Updates.ControlUpdateX) update).getValue() != Controls.Control.CENTER)
                    view.setX(GraphicsHelper.dpToPx(((Updates.ControlUpdateX) update).getValue()));
                else
                    view.setX(((Updates.ControlUpdateX) update).getValue());
            } else if (update instanceof Updates.ControlUpdateY) {
                control.setY(((Updates.ControlUpdateY) update).getValue());
                if (((Updates.ControlUpdateY) update).getValue() != Controls.Control.CENTER)
                    view.setY(GraphicsHelper.dpToPx(((Updates.ControlUpdateY) update).getValue()));
                else
                    view.setY(((Updates.ControlUpdateY) update).getValue());
            } else if (update instanceof Updates.ControlUpdateBackColor) {
                control.setBackColor(((Updates.ControlUpdateBackColor) update).getValue());
                View target = ((ViewGroup) view).getChildAt(0);
                target.setBackgroundColor(Color.parseColor(
                        ((Updates.ControlUpdateBackColor) update).getValue()));
            } if (update instanceof Updates.ControlUpdateBorderColor) {
                control.setBorderColor(((Updates.ControlUpdateBorderColor) update).getValue());
                ((RoundRectView) view).setBorderColor(Color.parseColor(
                        ((Updates.ControlUpdateBorderColor) update).getValue()));
            } else if (update instanceof Updates.ControlUpdateBorderWidth) {
                control.setBorderWidth(((Updates.ControlUpdateBorderWidth) update).getValue());
            } else if (update instanceof Updates.ControlUpdateRotationX) {
                control.setRotationX(((Updates.ControlUpdateRotationX) update).getValue());
                view.setRotationX(((Updates.ControlUpdateRotationX) update).getValue());
            } else if (update instanceof Updates.ControlUpdateRotationY) {
                control.setRotationY(((Updates.ControlUpdateRotationY) update).getValue());
                view.setRotationY(((Updates.ControlUpdateRotationY) update).getValue());
            } else if (update instanceof Updates.ControlUpdateRotation) {
                control.setRotation(((Updates.ControlUpdateRotation) update).getValue());
                view.setRotation(((Updates.ControlUpdateRotation) update).getValue());
            } else if (update instanceof Updates.ControlUpdateTopLeftRadius) {
                control.setTopLeftRadius(((Updates.ControlUpdateTopLeftRadius) update).getValue());
                ((RoundRectView) view).setTopLeftRadius(
                        ((Updates.ControlUpdateTopLeftRadius) update).getValue());
            } else if (update instanceof Updates.ControlUpdateTopRightRadius) {
                control.setTopLeftRadius(((Updates.ControlUpdateTopRightRadius) update).getValue());
                ((RoundRectView) view).setTopRightRadius(
                        ((Updates.ControlUpdateTopRightRadius) update).getValue());
            } else if (update instanceof Updates.ControlUpdateBottomLeftRadius) {
                control.setTopLeftRadius(((Updates.ControlUpdateBottomLeftRadius) update).getValue());
                ((RoundRectView) view).setBottomLeftRadius(
                        ((Updates.ControlUpdateBottomLeftRadius) update).getValue());
            } else if (update instanceof Updates.ControlUpdateBottomRightRadius) {
                control.setTopLeftRadius(((Updates.ControlUpdateBottomRightRadius) update).getValue());
                ((RoundRectView) view).setBottomRightRadius(
                        ((Updates.ControlUpdateBottomRightRadius) update).getValue());
            } else if (update instanceof Updates.ControlUpdateMarginLeft) {
                control.setMarginLeft(((Updates.ControlUpdateMarginLeft) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof RelativeLayout.LayoutParams) {
                    ((RelativeLayout.LayoutParams) params).setMargins(
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginLeft) update).getValue()),
                            ((RelativeLayout.LayoutParams) params).topMargin,
                            ((RelativeLayout.LayoutParams) params).rightMargin,
                            ((RelativeLayout.LayoutParams) params).bottomMargin);
                } else if (params instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) params).setMargins(
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginLeft) update).getValue()),
                            ((LinearLayout.LayoutParams) params).topMargin,
                            ((LinearLayout.LayoutParams) params).rightMargin,
                            ((LinearLayout.LayoutParams) params).bottomMargin);
                }
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdateMarginTop) {
                control.setMarginLeft(((Updates.ControlUpdateMarginTop) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof RelativeLayout.LayoutParams) {
                    ((RelativeLayout.LayoutParams) params).setMargins(
                            ((RelativeLayout.LayoutParams) params).leftMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginTop) update).getValue()),
                            ((RelativeLayout.LayoutParams) params).rightMargin,
                            ((RelativeLayout.LayoutParams) params).bottomMargin);
                } else if (params instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) params).setMargins(
                            ((LinearLayout.LayoutParams) params).leftMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginTop) update).getValue()),
                            ((LinearLayout.LayoutParams) params).rightMargin,
                            ((LinearLayout.LayoutParams) params).bottomMargin);
                }
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdateMarginRight) {
                control.setMarginLeft(((Updates.ControlUpdateMarginRight) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof RelativeLayout.LayoutParams) {
                    ((RelativeLayout.LayoutParams) params).setMargins(
                            ((RelativeLayout.LayoutParams) params).leftMargin,
                            ((RelativeLayout.LayoutParams) params).topMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginRight) update).getValue()),
                            ((RelativeLayout.LayoutParams) params).bottomMargin);
                } else if (params instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) params).setMargins(
                            ((LinearLayout.LayoutParams) params).leftMargin,
                            ((LinearLayout.LayoutParams) params).topMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginRight) update).getValue()),
                            ((LinearLayout.LayoutParams) params).bottomMargin);
                }
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdateMarginBottom) {
                control.setMarginLeft(((Updates.ControlUpdateMarginBottom) update).getValue());
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof RelativeLayout.LayoutParams) {
                    ((RelativeLayout.LayoutParams) params).setMargins(
                            ((RelativeLayout.LayoutParams) params).leftMargin,
                            ((RelativeLayout.LayoutParams) params).topMargin,
                            ((RelativeLayout.LayoutParams) params).rightMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginBottom) update).getValue()));
                } else if (params instanceof LinearLayout.LayoutParams) {
                    ((LinearLayout.LayoutParams) params).setMargins(
                            ((LinearLayout.LayoutParams) params).leftMargin,
                            ((LinearLayout.LayoutParams) params).topMargin,
                            ((LinearLayout.LayoutParams) params).rightMargin,
                            GraphicsHelper.dpToPx(
                                    ((Updates.ControlUpdateMarginBottom) update).getValue()));
                }
                view.setLayoutParams(params);
            } else if (update instanceof Updates.ControlUpdatePaddingLeft) {
                control.setPaddingLeft(((Updates.ControlUpdatePaddingLeft) update).getValue());
                view.setPadding(
                        GraphicsHelper.dpToPx(
                                ((Updates.ControlUpdatePaddingLeft) update).getValue()),
                        view.getPaddingTop(),
                        view.getPaddingRight(),
                        view.getPaddingBottom());
            } else if (update instanceof Updates.ControlUpdatePaddingTop) {
                control.setPaddingLeft(((Updates.ControlUpdatePaddingTop) update).getValue());
                view.setPadding(
                        view.getPaddingLeft(),
                        GraphicsHelper.dpToPx(
                                ((Updates.ControlUpdatePaddingTop) update).getValue()),
                        view.getPaddingRight(),
                        view.getPaddingBottom());
            } else if (update instanceof Updates.ControlUpdatePaddingRight) {
                control.setPaddingLeft(((Updates.ControlUpdatePaddingRight) update).getValue());
                view.setPadding(
                        view.getPaddingLeft(),
                        view.getPaddingTop(),
                        GraphicsHelper.dpToPx(
                                ((Updates.ControlUpdatePaddingRight) update).getValue()),
                        view.getPaddingBottom());
            } else if (update instanceof Updates.ControlUpdatePaddingBottom) {
                control.setPaddingLeft(((Updates.ControlUpdatePaddingBottom) update).getValue());
                view.setPadding(
                        view.getPaddingLeft(),
                        view.getPaddingTop(),
                        view.getPaddingRight(),
                        GraphicsHelper.dpToPx(
                                ((Updates.ControlUpdatePaddingBottom) update).getValue()));
            } else if (update instanceof Updates.ControlUpdateElevation) {
                control.setElevation(((Updates.ControlUpdateElevation) update).getValue());
                view.setElevation(GraphicsHelper.dpToPx(
                        ((Updates.ControlUpdateElevation) update).getValue()));
            } else if (update instanceof Updates.PanelCtrlAddControl) {
                if (control instanceof Controls.PanelCtrl) {
                    ((Controls.PanelCtrl) control).getControls().add(
                            ((Updates.PanelCtrlAddControl) update).getValue());
                    Tuple<View, List<Pair<Controls.Control, View>>
                            , Hashtable<String, Pair<Controls.Control, View>>> buildResult =
                            new UiInitiatorEngine(context, appName).buildViewTree(
                                    ((Controls.PanelCtrl) control).getLayoutType(),
                                    ((Updates.PanelCtrlAddControl) update).getValue());
                    idTable.putAll(buildResult.getItem3());
                    ((ViewGroup) view).addView(buildResult.getItem1());
                }
            } else if (update instanceof Updates.TextCtrlUpdateText) {
                if (control instanceof Controls.TextCtrl) {
                    ((Controls.TextCtrl) control).setText(
                            ((Updates.TextCtrlUpdateText) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((TextView) view).setText(((Updates.TextCtrlUpdateText) update).getValue());
                }
            } else if (update instanceof Updates.TextCtrlUpdateTextSize) {
                if (control instanceof Controls.TextCtrl) {
                    ((Controls.TextCtrl) control).setTextSize(
                            ((Updates.TextCtrlUpdateTextSize) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((TextView) view).setTextSize(((Updates.TextCtrlUpdateTextSize) update).getValue());
                }
            } else if (update instanceof Updates.TextCtrlUpdateTextColor) {
                if (control instanceof Controls.TextCtrl) {
                    ((Controls.TextCtrl) control).setTextColor(
                            ((Updates.TextCtrlUpdateTextColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((TextView) view).setTextColor(Color.parseColor(
                            ((Updates.TextCtrlUpdateTextColor) update).getValue()));
                }
            } else if (update instanceof Updates.TextCtrlUpdateGravityType) {
                if (control instanceof Controls.TextCtrl) {
                    ((Controls.TextCtrl) control).setGravityType(
                            ((Updates.TextCtrlUpdateGravityType) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    Updates.TextCtrlUpdateGravityType updateGravityType =
                            (Updates.TextCtrlUpdateGravityType) update;
                    if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.LEFT) {
                        ((TextView) view).setGravity(Gravity.LEFT);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.TOP) {
                        ((TextView) view).setGravity(Gravity.TOP);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.RIGHT) {
                        ((TextView) view).setGravity(Gravity.RIGHT);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.BOTTOM) {
                        ((TextView) view).setGravity(Gravity.BOTTOM);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.CENTER_VERTICAL) {
                        ((TextView) view).setGravity(Gravity.CENTER_VERTICAL);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.CENTER_HORIZONTAL) {
                        ((TextView) view).setGravity(Gravity.CENTER_HORIZONTAL);
                    } else if (updateGravityType.getValue() == Controls.TextCtrl.GravityType.CENTER) {
                        ((TextView) view).setGravity(Gravity.CENTER);
                    }
                }
            } else if (update instanceof Updates.ImageCtrlUpdateImageUrl) {
                if (control instanceof Controls.ImageCtrl) {
                    ((Controls.ImageCtrl) control).setImageUrl(
                            ((Updates.ImageCtrlUpdateImageUrl) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    Glide.with(context)
                            .load(((Updates.ImageCtrlUpdateImageUrl) update).getValue())
                            .into((ImageView) view);
                }
            } else if (update instanceof Updates.ImageCtrlUpdateScaleType) {
                if (control instanceof Controls.ImageCtrl) {
                    ((Controls.ImageCtrl) control).setScaleType(
                            ((Updates.ImageCtrlUpdateScaleType) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((ImageView) view).setScaleType(ImageView.ScaleType.valueOf(
                            ((Updates.ImageCtrlUpdateScaleType) update).getValue().toString()));
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateHint) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setHint(
                            ((Updates.InputFieldCtrlUpdateHint) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((EditText) view).setHint(((Updates.InputFieldCtrlUpdateHint) update).getValue());
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateHintColor) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setHintColor(
                            ((Updates.InputFieldCtrlUpdateHintColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((EditText) view).setHintTextColor(Color.parseColor(
                            ((Updates.InputFieldCtrlUpdateHintColor) update).getValue()));
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateText) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setText(
                            ((Updates.InputFieldCtrlUpdateText) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((EditText) view).setText(
                            ((Updates.InputFieldCtrlUpdateText) update).getValue());
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateTextColor) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setTextColor(
                            ((Updates.InputFieldCtrlUpdateTextColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((EditText) view).setTextColor(Color.parseColor(
                            ((Updates.InputFieldCtrlUpdateTextColor) update).getValue()));
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateTextSize) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setTextSize(
                            ((Updates.InputFieldCtrlUpdateTextSize) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((EditText) view).setTextSize(
                            ((Updates.InputFieldCtrlUpdateTextSize) update).getValue());
                }
            } else if (update instanceof Updates.InputFieldCtrlUpdateLineColor) {
                if (control instanceof Controls.InputFieldCtrl) {
                    ((Controls.InputFieldCtrl) control).setLineColor(
                            ((Updates.InputFieldCtrlUpdateLineColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    view.getBackground().setColorFilter(Color.parseColor(
                            ((Updates.InputFieldCtrlUpdateLineColor) update).getValue())
                            , PorterDuff.Mode.SRC_ATOP);
                }
            } else if (update instanceof Updates.ButtonCtrlUpdateCaption) {
                if (control instanceof Controls.ButtonCtrl) {
                    ((Controls.ButtonCtrl) control).setCaption(
                            ((Updates.ButtonCtrlUpdateCaption) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((Button) view).setText(((Updates.ButtonCtrlUpdateCaption) update).getValue());
                }
            } else if (update instanceof Updates.ButtonCtrlUpdateCaptionSize) {
                if (control instanceof Controls.ButtonCtrl) {
                    ((Controls.ButtonCtrl) control).setCaptionSize(
                            ((Updates.ButtonCtrlUpdateCaptionSize) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((Button) view).setTextSize(((Updates.ButtonCtrlUpdateCaptionSize) update).getValue());
                }
            } else if (update instanceof Updates.ButtonCtrlUpdateCaptionColor) {
                if (control instanceof Controls.ButtonCtrl) {
                    ((Controls.ButtonCtrl) control).setCaptionColor(
                            ((Updates.ButtonCtrlUpdateCaptionColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((Button) view).setTextColor(Color.parseColor(
                            ((Updates.ButtonCtrlUpdateCaptionColor) update).getValue()));
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateCircleBroken) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setCircleBroken(
                            ((Updates.ProgressCtrlUpdateCircleBroken) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setCircleBroken(
                                ((Updates.ProgressCtrlUpdateCircleBroken) update).isValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateFillEnabled) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setFillEnabled(
                            ((Updates.ProgressCtrlUpdateFillEnabled) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setFillEnabled(
                                ((Updates.ProgressCtrlUpdateFillEnabled) update).isValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateGraduatedEnabled) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setGraduatedEnabled(
                            ((Updates.ProgressCtrlUpdateGraduatedEnabled) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setGraduatedEnabled(
                                ((Updates.ProgressCtrlUpdateGraduatedEnabled) update).isValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressCornerRadius) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressCornerRadius(
                            ((Updates.ProgressCtrlUpdateProgressCornerRadius) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        Log.d(TAG, "Illegal Update");
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressCornerRadius(
                                ((Updates.ProgressCtrlUpdateProgressCornerRadius) update).getValue());
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressTextColor) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressTextColor(
                            ((Updates.ProgressCtrlUpdateProgressTextColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setProgressTextColor(Color.parseColor(
                                ((Updates.ProgressCtrlUpdateProgressTextColor) update).getValue()));
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressTextColor(Color.parseColor(
                                ((Updates.ProgressCtrlUpdateProgressTextColor) update).getValue()));
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressTextMoved) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressTextMoved(
                            ((Updates.ProgressCtrlUpdateProgressTextMoved) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        Log.d(TAG, "Illegal Update");
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressTextMoved(
                                ((Updates.ProgressCtrlUpdateProgressTextMoved) update).isValue());
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressTextPaddingBottom) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressTextPaddingBottom(
                            ((Updates.ProgressCtrlUpdateProgressTextPaddingBottom) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        Log.d(TAG, "Illegal Update");
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressTextPaddingBottom(
                                ((Updates.ProgressCtrlUpdateProgressTextPaddingBottom) update).getValue());
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressTextSize) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressTextSize(
                            ((Updates.ProgressCtrlUpdateProgressTextSize) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setProgressTextSize(
                                ((Updates.ProgressCtrlUpdateProgressTextSize) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressTextSize(
                                ((Updates.ProgressCtrlUpdateProgressTextSize) update).getValue());
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateProgressTextVisibility) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setProgressTextVisibility(
                            ((Updates.ProgressCtrlUpdateProgressTextVisibility) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setProgressTextVisibility(
                                ((Updates.ProgressCtrlUpdateProgressTextVisibility) update).isValue());
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setProgressTextVisibility(
                                ((Updates.ProgressCtrlUpdateProgressTextVisibility) update).isValue());
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateScaleZoneCornerRadius) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setScaleZoneCornerRadius(
                            ((Updates.ProgressCtrlUpdateScaleZoneCornerRadius) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setScaleZoneCornerRadius(
                                ((Updates.ProgressCtrlUpdateScaleZoneCornerRadius) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateScaleZoneLength) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setScaleZoneLength(
                            ((Updates.ProgressCtrlUpdateScaleZoneLength) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setScaleZoneLength(
                                ((Updates.ProgressCtrlUpdateScaleZoneLength) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateScaleZonePadding) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setScaleZonePadding(
                            ((Updates.ProgressCtrlUpdateScaleZonePadding) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setScaleZonePadding(
                                ((Updates.ProgressCtrlUpdateScaleZonePadding) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateScaleZoneWidth) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setScaleZoneWidth(
                            ((Updates.ProgressCtrlUpdateScaleZoneWidth) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setScaleZoneWidth(
                                ((Updates.ProgressCtrlUpdateScaleZoneWidth) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        Log.d(TAG, "Illegal Update");
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateTrackColor) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setTrackColor(
                            ((Updates.ProgressCtrlUpdateTrackColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setTrackColor(Color.parseColor(
                                ((Updates.ProgressCtrlUpdateTrackColor) update).getValue()));
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setTrackColor(Color.parseColor(
                                ((Updates.ProgressCtrlUpdateTrackColor) update).getValue()));
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateTrackEnabled) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setTrackEnabled(
                            ((Updates.ProgressCtrlUpdateTrackEnabled) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setTrackEnabled(
                                ((Updates.ProgressCtrlUpdateTrackEnabled) update).isValue());
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setTrackEnabled(
                                ((Updates.ProgressCtrlUpdateTrackEnabled) update).isValue());
                    }
                }
            } else if (update instanceof Updates.ProgressCtrlUpdateTrackWidth) {
                if (control instanceof Controls.ProgressCtrl) {
                    ((Controls.ProgressCtrl) control).setTrackWidth(
                            ((Updates.ProgressCtrlUpdateTrackWidth) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (view instanceof CircleProgressView) {
                        ((CircleProgressView) view).setTrackWidth(
                                ((Updates.ProgressCtrlUpdateTrackWidth) update).getValue());
                    } else if (view instanceof HorizontalProgressView) {
                        ((HorizontalProgressView) view).setTrackWidth(
                                ((Updates.ProgressCtrlUpdateTrackWidth) update).getValue());
                    }
                }
            } else if (update instanceof Updates.VideoPlayerCtrlUpdateVideoUrl) {
                if (control instanceof Controls.VideoPlayerCtrl) {
                    ((Controls.VideoPlayerCtrl) control).setVideoUrl(
                            ((Updates.VideoPlayerCtrlUpdateVideoUrl) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((VideoView) view).getPlayer().stop();
                    ((VideoView) view).setVideoPath(
                            Uri.parse(((Updates.VideoPlayerCtrlUpdateVideoUrl) update)
                                    .getValue()).toString());
                    if (((Controls.VideoPlayerCtrl) control).isPlaying())
                        ((VideoView) view).getPlayer().start();
                }
            } else if (update instanceof Updates.VideoPlayerCtrlUpdateProgress) {
                if (control instanceof Controls.VideoPlayerCtrl) {
                    ((Controls.VideoPlayerCtrl) control).setProgress(
                            ((Updates.VideoPlayerCtrlUpdateProgress) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((VideoView) view).getPlayer().seekTo(
                            ((Updates.VideoPlayerCtrlUpdateProgress) update).getValue());
                }
            } else if (update instanceof Updates.VideoPlayerCtrlUpdatePlaying) {
                if (control instanceof Controls.VideoPlayerCtrl) {
                    ((Controls.VideoPlayerCtrl) control).setPlaying(
                            ((Updates.VideoPlayerCtrlUpdatePlaying) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    if (((Updates.VideoPlayerCtrlUpdatePlaying) update).isValue())
                        ((VideoView) view).getPlayer().start();
                    else
                        ((VideoView) view).getPlayer().stop();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateAxisColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setAxisColor(
                            ((Updates.LineChartCtrlUpdateAxisColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineChartView) view).setAxisColor(Color.parseColor(
                            ((Updates.LineChartCtrlUpdateAxisColor) update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateDotsColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setDotsColor(
                            ((Updates.LineChartCtrlUpdateDotsColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet)((LineChartView) view).getData().get(0)).setDotsColor(
                            Color.parseColor(((Updates.LineChartCtrlUpdateDotsColor) update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateDotsRadius) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setDotsRadius(
                            ((Updates.LineChartCtrlUpdateDotsRadius) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet) ((LineChartView) view).getData().get(0)).setDotsRadius(
                            GraphicsHelper.dpToPx(((Updates.LineChartCtrlUpdateDotsRadius)
                                    update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateDotsStrokeColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setDotsStrokeColor(
                            ((Updates.LineChartCtrlUpdateDotsStrokeColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet) ((LineChartView) view).getData().get(0)).setDotsStrokeColor(
                            Color.parseColor(((Updates.LineChartCtrlUpdateDotsStrokeColor)
                                    update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateDotsStrokeThickness) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setDotsStrokeThickness(
                            ((Updates.LineChartCtrlUpdateDotsStrokeThickness)
                                    update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet) ((LineChartView) view).getData().get(0)).setDotsStrokeThickness(
                            GraphicsHelper.dpToPx(((Updates.LineChartCtrlUpdateDotsStrokeThickness)
                                    update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateFillColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setFillColor(
                            ((Updates.LineChartCtrlUpdateFillColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet) ((LineChartView) view).getData().get(0)).setFill(
                            Color.parseColor(((Updates.LineChartCtrlUpdateFillColor) update)
                                    .getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateGradients) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setGradientColors(
                            ((Updates.LineChartCtrlUpdateGradients) update).getValue());
                    ((Controls.LineChartCtrl) control).setGradientValues(
                            ((Updates.LineChartCtrlUpdateGradients) update).getValue2());
                    view = ((ViewGroup) view).getChildAt(0);
                    List<Data.StringValue> gradientColorsList =
                            ((Updates.LineChartCtrlUpdateGradients) update).getValue();
                    List<Data.FloatValue> gradientValuesList =
                            ((Updates.LineChartCtrlUpdateGradients) update).getValue2();
                    int[] gradientColors = new int[gradientColorsList.size()];
                    float[] gradientValues = new float[gradientValuesList.size()];
                    int counter = 0;
                    Iterator<Data.StringValue> colorIterator = gradientColorsList.iterator();
                    Iterator<Data.FloatValue> valueIterator = gradientValuesList.iterator();
                    while (colorIterator.hasNext() && valueIterator.hasNext()) {
                        Data.StringValue color = colorIterator.next();
                        Data.FloatValue value = valueIterator.next();
                        gradientColors[counter] = Color.parseColor(color.getValue());
                        gradientValues[counter] = value.getValue();
                        counter++;
                    }
                    LineSet lineSet = (LineSet) ((LineChartView) view).getData().get(0);
                    if (gradientColors.length > 0)
                        lineSet.setGradientFill(gradientColors, gradientValues);
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLabelsColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLabelsColor(
                            ((Updates.LineChartCtrlUpdateLabelsColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineChartView) view).setLabelsColor(Color.parseColor(
                            ((Updates.LineChartCtrlUpdateLabelsColor) update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineBeginAt) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineBeginAt(
                            ((Updates.LineChartCtrlUpdateLineBeginAt) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet)((LineChartView) view).getData().get(0)).beginAt(
                            ((Updates.LineChartCtrlUpdateLineBeginAt) update).getValue());
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineColor) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineColor(
                            ((Updates.LineChartCtrlUpdateLineColor) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet)((LineChartView) view).getData().get(0)).setColor(Color.parseColor(
                            ((Updates.LineChartCtrlUpdateLineColor) update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineDashedIntervals) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineDashedIntervals(
                            ((Updates.LineChartCtrlUpdateLineDashedIntervals) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    List<Data.FloatValue> dashedIntervalsList =
                            ((Updates.LineChartCtrlUpdateLineDashedIntervals) update).getValue();
                    float[] lineDashedIntervals = new float[dashedIntervalsList.size()];
                    int counter = 0;
                    for (Data.FloatValue floatValue : dashedIntervalsList) {
                        lineDashedIntervals[counter] = floatValue.getValue();
                        counter++;
                    }
                    ((LineSet) ((LineChartView) view).getData().get(0)).setDashed(lineDashedIntervals);
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineEndAt) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineEndAt(
                            ((Updates.LineChartCtrlUpdateLineEndAt) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet)((LineChartView) view).getData().get(0)).endAt(
                            ((Updates.LineChartCtrlUpdateLineEndAt) update).getValue());
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineSmooth) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineSmooth(
                            ((Updates.LineChartCtrlUpdateLineSmooth) update).isValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet) ((LineChartView) view).getData().get(0)).setSmooth(
                            ((Updates.LineChartCtrlUpdateLineSmooth) update).isValue());
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdateLineThickness) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setLineThickness(
                            GraphicsHelper.dpToPx(((Updates.LineChartCtrlUpdateLineThickness)
                                    update).getValue()));
                    view = ((ViewGroup) view).getChildAt(0);
                    ((LineSet)((LineChartView) view).getData().get(0)).setThickness(
                            GraphicsHelper.dpToPx(((Updates.LineChartCtrlUpdateLineThickness)
                                    update).getValue()));
                    ((LineChartView) view).notifyDataUpdate();
                    ((LineChartView) view).show();
                }
            } else if (update instanceof Updates.LineChartCtrlUpdatePoints) {
                if (control instanceof Controls.LineChartCtrl) {
                    ((Controls.LineChartCtrl) control).setPoints(
                            ((Updates.LineChartCtrlUpdatePoints) update).getValue());
                    view = ((ViewGroup) view).getChildAt(0);
                    LineChartView lineChartView = (LineChartView) view;
                    Controls.LineChartCtrl chartEl = (Controls.LineChartCtrl) control;
                    LineSet dataset = new UiInitiatorEngine(context, appName).initLineChartView(chartEl);
                    lineChartView.getData().remove(0);
                    lineChartView.addData(dataset);
                    lineChartView.notifyDataUpdate();
                    lineChartView.show();
                }
            }
        }
    }

    public void updateBatchUi(Hashtable<String, Pair<Controls.Control, View>> idTable, List<Updates.Update> updates) {
        for (Updates.Update update : updates) {
            updateUi(idTable, update);
        }
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

package kasper.android.pulseframework.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import kasper.android.pulseframework.R;
import kasper.android.pulseframework.engine.UiEngine;
import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Tuple;
import kasper.android.pulseframework.models.Updates;
import kasper.android.pulseframework.utils.GraphicsHelper;

public class PulseView extends RelativeLayout {

    private Hashtable<String, Pair<Controls.Control, View>> idTable;

    public PulseView(Context context) {
        super(context);
        init();
    }

    public PulseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        GraphicsHelper.setup(getContext());
        this.setFocusableInTouchMode(true);
    }

    public void buildUi(Controls.Control control) {
        this.removeAllViews();
        UiEngine uiEngine = new UiEngine(getContext(), getResources().getString(R.string.app_name));
        Tuple<View, List<Pair<Controls.Control, View>>
                , Hashtable<String, Pair<Controls.Control, View>>> result =
                uiEngine.buildViewTree(Controls.PanelCtrl.LayoutType.RELATIVE, control);
        View view = result.getItem1();
        idTable = result.getItem3();
        this.addView(view);
    }

    public void buildUi(String json) {
        ObjectMapper objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Controls.Control root = objectMapper.readValue(json, Controls.Control.class);
            buildUi(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateUi(Updates.Update update) {
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
                View target = view;
                if (target instanceof CardView) {
                    target = ((CardView) target).getChildAt(0);
                    if (target instanceof CardView) {
                        target = ((CardView) target).getChildAt(0);
                    }
                }
                target.setBackgroundColor(Color.parseColor(
                        ((Updates.ControlUpdateBackColor) update).getValue()));
            } else if (update instanceof Updates.ControlUpdateBorderColor) {
                control.setBorderColor(((Updates.ControlUpdateBorderColor) update).getValue());

                View target = view;
                if (target instanceof CardView) {
                    target = ((CardView) target).getChildAt(0);
                    if (target instanceof CardView) {
                        target = ((CardView) target).getChildAt(0);
                    }
                }

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
            }
        }
    }
}

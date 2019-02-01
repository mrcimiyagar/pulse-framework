package kasper.android.pulseframework.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import kasper.android.pulseframework.R;
import kasper.android.pulseframework.engines.UiInitiatorEngine;
import kasper.android.pulseframework.engines.UiUpdaterEngine;
import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Tuple;
import kasper.android.pulseframework.models.Updates;
import kasper.android.pulseframework.utils.GraphicsHelper;

public class PulseView extends RelativeLayout {

    private Hashtable<String, Pair<Controls.Control, View>> idTable;
    private UiInitiatorEngine uiInitiatorEngine;
    private UiUpdaterEngine uiUpdaterEngine;

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
        this.idTable = new Hashtable<>();
        GraphicsHelper.setup(getContext());
        this.setFocusableInTouchMode(true);
    }

    public void setup(AppCompatActivity activity) {
        this.uiInitiatorEngine = new UiInitiatorEngine(
                getContext(), getResources().getString(R.string.app_name),
                activity::runOnUiThread);
        this.uiUpdaterEngine = new UiUpdaterEngine(
                getContext(), getResources().getString(R.string.app_name),
                activity::runOnUiThread);
    }

    public void buildUi(Controls.Control control) {
        this.removeAllViews();
        Tuple<View, List<Pair<Controls.Control, View>>
                , Hashtable<String, Pair<Controls.Control, View>>> result =
                uiInitiatorEngine.buildViewTree(Controls.PanelCtrl.LayoutType.RELATIVE, control);
        View view = result.getItem1();
        idTable = result.getItem3();
        this.addView(view);
    }

    public void buildUi(String json) {
        try {
            Controls.Control root = initMapper().readValue(json, Controls.Control.class);
            buildUi(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateUi(String json) {
        try {
            Updates.Update update = initMapper().readValue(json, Updates.Update.class);
            updateUi(update);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateUi(Updates.Update update) {
        uiUpdaterEngine.updateUi(idTable, update);
    }

    public void updateBatchUi(String json) {
        try {
            Updates.Update update = initMapper().readValue(json, new TypeReference<List<Updates.Update>>(){});
            updateUi(update);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateBatchUi(List<Updates.Update> updates) {
        uiUpdaterEngine.updateBatchUi(idTable, updates);
    }

    private ObjectMapper initMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}

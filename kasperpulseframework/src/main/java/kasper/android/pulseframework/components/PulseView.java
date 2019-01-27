package kasper.android.pulseframework.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kasper.android.pulseframework.R;
import kasper.android.pulseframework.engine.Renderer;
import kasper.android.pulseframework.models.Elements;
import kasper.android.pulseframework.utils.GraphicsHelper;

public class PulseView extends RelativeLayout {

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

    public void render(Elements.Element element) {
        this.removeAllViews();
        Renderer renderer = new Renderer(getContext(), getResources().getString(R.string.app_name));
        View view = renderer.render(Elements.PanelEl.LayoutType.RELATIVE, element);
        this.addView(view);
    }

    public void render(String json) {
        ObjectMapper objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Elements.Element root = objectMapper.readValue(json, Elements.Element.class);
            render(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

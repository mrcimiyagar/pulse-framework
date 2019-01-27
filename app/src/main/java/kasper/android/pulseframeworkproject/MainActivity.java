package kasper.android.pulseframeworkproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kasper.android.pulseframework.components.PulseView;
import kasper.android.pulseframework.models.Data;
import kasper.android.pulseframework.models.Elements;

public class MainActivity extends AppCompatActivity {

    String colorDark = "#1B2735";
    String colorDarkDark = "#151E27";
    String colorDarkLight = "#212E3E";
    String colorBlue = "#456A94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Elements.ScrollerEl scrollerEl = new Elements.ScrollerEl();
        scrollerEl.setWidth(Elements.Element.MATCH_PARENT);
        scrollerEl.setHeight(Elements.Element.MATCH_PARENT);

        Elements.PanelEl containerEl = new Elements.PanelEl();
        containerEl.setWidth(Elements.Element.MATCH_PARENT);
        containerEl.setHeight(Elements.Element.WRAP_CONTENT);
        containerEl.setLayoutType(Elements.PanelEl.LayoutType.LINEAR_VERTICAL);
        containerEl.setElements(new ArrayList<>());
        scrollerEl.setPanel(containerEl);

        Elements.PanelEl welcomeMessageEl = new Elements.PanelEl();
        welcomeMessageEl.setWidth(300);
        welcomeMessageEl.setHeight(72);
        welcomeMessageEl.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        welcomeMessageEl.setBackColor(colorDarkLight);
        welcomeMessageEl.setCornerRadius(36);
        welcomeMessageEl.setX(Elements.Element.CENTER);
        welcomeMessageEl.setMarginTop(56);
        welcomeMessageEl.setElevation(6);
        welcomeMessageEl.setElements(new ArrayList<>());
        containerEl.getElements().add(welcomeMessageEl);

        Elements.ImageEl profileImageEl = new Elements.ImageEl();
        profileImageEl.setWidth(72);
        profileImageEl.setHeight(72);
        profileImageEl.setScaleType(Elements.ImageEl.ImageScaleType.CENTER_CROP);
        profileImageEl.setCornerRadius(36);
        profileImageEl.setElevation(6);
        profileImageEl.setImageUrl("https://quera.ir/media/CACHE/images/public/avatars/dbb966f617244cd8beb7a7bca29fa2c5/4ea69e7d275c233761a727dfb97e4fc1.jpg");
        welcomeMessageEl.getElements().add(profileImageEl);

        Elements.PanelEl welcomeTextsEl = new Elements.PanelEl();
        welcomeTextsEl.setWidth(Elements.Element.MATCH_PARENT);
        welcomeTextsEl.setHeight(Elements.Element.MATCH_PARENT);
        welcomeTextsEl.setLayoutType(Elements.PanelEl.LayoutType.LINEAR_VERTICAL);
        welcomeTextsEl.setMarginLeft(80);
        welcomeTextsEl.setMarginTop(6);
        welcomeTextsEl.setMarginBottom(6);
        welcomeTextsEl.setElements(new ArrayList<>());
        welcomeMessageEl.getElements().add(welcomeTextsEl);

        Elements.TextEl profileTitleEl = new Elements.TextEl();
        profileTitleEl.setWidth(Elements.Element.WRAP_CONTENT);
        profileTitleEl.setHeight(32);
        profileTitleEl.setText("Keyhan Mohammadi");
        profileTitleEl.setTextColor(colorBlue);
        profileTitleEl.setTextSize(16);
        profileTitleEl.setGravityType(Elements.TextEl.GravityType.CENTER_VERTICAL);
        welcomeTextsEl.getElements().add(profileTitleEl);

        Elements.TextEl messageEl = new Elements.TextEl();
        messageEl.setWidth(Elements.Element.WRAP_CONTENT);
        messageEl.setHeight(28);
        messageEl.setText("Welcome to home in sky city");
        messageEl.setTextColor(colorBlue);
        messageEl.setTextSize(14);
        messageEl.setGravityType(Elements.TextEl.GravityType.CENTER_VERTICAL);
        welcomeTextsEl.getElements().add(messageEl);

        Elements.PanelEl clockEl = new Elements.PanelEl();
        clockEl.setWidth(300);
        clockEl.setHeight(300);
        clockEl.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        clockEl.setBackColor(colorDarkLight);
        clockEl.setCornerRadius(150);
        clockEl.setMarginTop(56);
        clockEl.setX(Elements.Element.CENTER);
        clockEl.setElements(new ArrayList<>());
        containerEl.getElements().add(clockEl);

        int x = 128;
        int y = 0;
        for (int counter = 0; counter < 12; counter++) {
            double cos = Math.cos(Math.toRadians(counter * 30 - 90));
            double sin = Math.sin(Math.toRadians(counter * 30 - 90));
            int posX = (int) (x * cos - y * sin) + 128 + 4;
            int posY = (int) (x * sin + y * cos) + 128 + 4;
            Elements.TextEl hourEl = new Elements.TextEl();
            hourEl.setX(posX);
            hourEl.setY(posY);
            hourEl.setWidth(32);
            hourEl.setHeight(32);
            hourEl.setText(counter == 0 ? "12" : counter + "");
            hourEl.setGravityType(Elements.TextEl.GravityType.CENTER);
            hourEl.setTextColor(colorBlue);
            hourEl.setTextSize(18);
            clockEl.getElements().add(hourEl);
        }

        for (int counter = 0; counter < 60; counter++) {
            Elements.PanelEl panelEl = new Elements.PanelEl();
            panelEl.setWidth(2);
            panelEl.setHeight(300);
            panelEl.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
            panelEl.setRotation(counter * 6);
            panelEl.setX(150 - 4 / 2);
            clockEl.getElements().add(panelEl);

            Elements.PanelEl secondEl = new Elements.PanelEl();
            secondEl.setWidth(Elements.Element.MATCH_PARENT);
            secondEl.setHeight(8);
            secondEl.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
            secondEl.setBackColor(colorBlue);
            panelEl.getElements().add(secondEl);
        }

        Elements.PanelEl hourHand = new Elements.PanelEl();
        hourHand.setWidth(150);
        hourHand.setHeight(8);
        hourHand.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        hourHand.setX(Elements.Element.CENTER);
        hourHand.setY(clockEl.getHeight() / 2 - hourHand.getHeight() / 2);
        hourHand.setElements(new ArrayList<>());
        hourHand.setRotation(135);
        clockEl.getElements().add(hourHand);

        Elements.PanelEl hourHalfHand = new Elements.PanelEl();
        hourHalfHand.setWidth(hourHand.getWidth() / 2);
        hourHalfHand.setHeight(Elements.Element.MATCH_PARENT);
        hourHalfHand.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        hourHalfHand.setBackColor(colorBlue);
        hourHalfHand.setX(hourHand.getWidth() / 2);
        hourHalfHand.setElements(new ArrayList<>());
        hourHalfHand.setCornerRadius(4);
        hourHand.getElements().add(hourHalfHand);

        Elements.PanelEl minuteHand = new Elements.PanelEl();
        minuteHand.setWidth(200);
        minuteHand.setHeight(8);
        minuteHand.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        minuteHand.setX(Elements.Element.CENTER);
        minuteHand.setY(clockEl.getHeight() / 2 - minuteHand.getHeight() / 2);
        minuteHand.setElements(new ArrayList<>());
        clockEl.getElements().add(minuteHand);

        Elements.PanelEl minuteHalfHand = new Elements.PanelEl();
        minuteHalfHand.setWidth(minuteHand.getWidth() / 2);
        minuteHalfHand.setHeight(Elements.Element.MATCH_PARENT);
        minuteHalfHand.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        minuteHalfHand.setBackColor(colorBlue);
        minuteHalfHand.setX(minuteHand.getWidth() / 2);
        minuteHalfHand.setElements(new ArrayList<>());
        minuteHalfHand.setCornerRadius(4);
        minuteHand.getElements().add(minuteHalfHand);

        Elements.PanelEl calUp = new Elements.PanelEl();
        calUp.setWidth(300);
        calUp.setHeight(72);
        calUp.setX(Elements.Element.CENTER);
        calUp.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        calUp.setElements(new ArrayList<>());
        calUp.setElevation(8);
        calUp.setMarginTop(56);
        containerEl.getElements().add(calUp);

        Elements.PanelEl calUp1 = new Elements.PanelEl();
        calUp1.setWidth(16);
        calUp1.setHeight(72);
        calUp1.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        calUp1.setBackColor(colorBlue);
        calUp1.setX(56);
        calUp1.setCornerRadius(4);
        calUp1.setElements(new ArrayList<>());
        calUp.getElements().add(calUp1);

        Elements.PanelEl calUp2 = new Elements.PanelEl();
        calUp2.setWidth(16);
        calUp2.setHeight(112);
        calUp2.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        calUp2.setBackColor(colorBlue);
        calUp2.setX(calUp.getWidth() - 56 - calUp2.getWidth());
        calUp2.setCornerRadius(4);
        calUp2.setElements(new ArrayList<>());
        calUp.getElements().add(calUp2);

        Elements.PanelEl calBox = new Elements.PanelEl();
        calBox.setWidth(300);
        calBox.setHeight(328);
        calBox.setMarginTop(-36);
        calBox.setBackColor(colorDarkLight);
        calBox.setCornerRadius(16);
        calBox.setX(Elements.Element.CENTER);
        calBox.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        calBox.setElements(new ArrayList<>());
        containerEl.getElements().add(calBox);

        Elements.PanelEl innerCalBox = new Elements.PanelEl();
        innerCalBox.setWidth(280);
        innerCalBox.setHeight(Elements.Element.MATCH_PARENT);
        innerCalBox.setLayoutType(Elements.PanelEl.LayoutType.RELATIVE);
        innerCalBox.setElements(new ArrayList<>());
        innerCalBox.setMarginTop(40);
        innerCalBox.setX(Elements.Element.CENTER);
        calBox.getElements().add(innerCalBox);

        Elements.TextEl dateEl = new Elements.TextEl();
        dateEl.setWidth(Elements.Element.WRAP_CONTENT);
        dateEl.setHeight(Elements.Element.WRAP_CONTENT);
        dateEl.setText("January 2019");
        dateEl.setTextSize(20);
        dateEl.setTextColor(colorBlue);
        dateEl.setY(8);
        dateEl.setX(Elements.Element.CENTER);
        innerCalBox.getElements().add(dateEl);

        String[] dayNames = new String[]{
                "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"
        };

        for (int counter = 0; counter < 7; counter++) {
            Elements.TextEl textEl = new Elements.TextEl();
            textEl.setText(dayNames[counter]);
            textEl.setWidth(40);
            textEl.setHeight(32);
            textEl.setTextColor(colorBlue);
            textEl.setTextSize(16);
            textEl.setGravityType(Elements.TextEl.GravityType.CENTER);
            textEl.setX(counter * 40);
            textEl.setY(48);
            innerCalBox.getElements().add(textEl);
        }

        String[] dayNums = new String[6 * 7];

        int num = 30;
        for (int counter = 0; counter < dayNums.length; counter++) {
            dayNums[counter] = num + "";
            num++;
            if (num > 31)
                num = 1;
        }

        for (int counter = 0; counter < 6; counter++) {
            for (int inCounter = 0; inCounter < 7; inCounter++) {
                Elements.TextEl textEl = new Elements.TextEl();
                textEl.setWidth(40);
                textEl.setHeight(32);
                textEl.setText(dayNums[counter * 7 + inCounter]);
                textEl.setGravityType(Elements.TextEl.GravityType.CENTER);
                if (counter == 0 && (inCounter == 0 || inCounter == 1))
                    textEl.setTextColor("#555555");
                else if (counter == 4 && (inCounter == 5 || inCounter == 6))
                    textEl.setTextColor("#555555");
                else if (counter == 5)
                    textEl.setTextColor("#555555");
                else
                    textEl.setTextColor(colorBlue);
                if (textEl.getText().equals("27")) {
                    textEl.setBorderColor(colorBlue);
                    textEl.setBorderWidth(2);
                    textEl.setCornerRadius(4);
                    textEl.setBackColor(colorDarkLight);
                }
                textEl.setTextSize(16);
                textEl.setX(inCounter * 40);
                textEl.setY(counter * 32 + 32 + 48);
                innerCalBox.getElements().add(textEl);
            }
        }

        Elements.LineChartEl lineChartEl = new Elements.LineChartEl();
        lineChartEl.setWidth(300);
        lineChartEl.setHeight(260);
        lineChartEl.setLabelsColor(colorBlue);
        lineChartEl.setAxisColor(colorBlue);
        lineChartEl.setDotsColor(colorBlue);
        lineChartEl.setLineColor(colorBlue);
        lineChartEl.setLineThickness(2);
        lineChartEl.setDotsRadius(4);
        lineChartEl.setFillColor(colorDarkLight);

        List<Data.Point> points = new ArrayList<>();
        Random random = new Random();
        for (int counter = 0; counter < 24; counter++) {
            Data.Point point = new Data.Point();
            if (counter % 5 == 0)
                point.setLabel("A" + counter / 5);
            else
                point.setLabel("");
            point.setValue(random.nextInt(101));
            points.add(point);
        }

        lineChartEl.setPoints(points);
        lineChartEl.setX(Elements.Element.CENTER);
        lineChartEl.setMarginTop(56);
        containerEl.getElements().add(lineChartEl);

        Elements.VideoPlayerEl videoPlayerEl = new Elements.VideoPlayerEl();
        videoPlayerEl.setWidth(300);
        videoPlayerEl.setHeight(260);
        videoPlayerEl.setVideoUrl("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4");
        videoPlayerEl.setX(Elements.Element.CENTER);
        videoPlayerEl.setMarginTop(56);
        videoPlayerEl.setMarginBottom(56);
        containerEl.getElements().add(videoPlayerEl);

        try {
            String json = new ObjectMapper().writeValueAsString(scrollerEl);
            Log.d("Pulse", json);
            PulseView pulseView = findViewById(R.id.pulseView);
            pulseView.render(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

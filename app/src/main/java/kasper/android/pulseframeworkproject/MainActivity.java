package kasper.android.pulseframeworkproject;

import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import kasper.android.pulseframework.components.PulseView;
import kasper.android.pulseframework.models.Data;
import kasper.android.pulseframework.models.Controls;
import kasper.android.pulseframework.models.Updates;

public class MainActivity extends AppCompatActivity {

    String colorDark = "#1B2735";
    String colorDarkDark = "#151E27";
    String colorDarkLight = "#212E3E";
    String colorBlue = "#456A94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controls.ScrollerCtrl scrollerCtrl = new Controls.ScrollerCtrl();
        scrollerCtrl.setWidth(Controls.Control.MATCH_PARENT);
        scrollerCtrl.setHeight(Controls.Control.MATCH_PARENT);

        Controls.PanelCtrl containerEl = new Controls.PanelCtrl();
        containerEl.setWidth(Controls.Control.MATCH_PARENT);
        containerEl.setHeight(Controls.Control.WRAP_CONTENT);
        containerEl.setLayoutType(Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL);
        containerEl.setControls(new ArrayList<>());
        scrollerCtrl.setPanel(containerEl);

        Controls.PanelCtrl welcomeMessageEl = new Controls.PanelCtrl();
        welcomeMessageEl.setId("WelcomeMessageEl");
        welcomeMessageEl.setWidth(300);
        welcomeMessageEl.setHeight(72);
        welcomeMessageEl.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        welcomeMessageEl.setBackColor(colorDarkLight);
        welcomeMessageEl.setCornerRadius(36);
        welcomeMessageEl.setX(Controls.Control.CENTER);
        welcomeMessageEl.setMarginTop(56);
        welcomeMessageEl.setElevation(6);
        welcomeMessageEl.setControls(new ArrayList<>());
        containerEl.getControls().add(welcomeMessageEl);

        Controls.ImageCtrl profileImageCtrl = new Controls.ImageCtrl();
        profileImageCtrl.setWidth(72);
        profileImageCtrl.setHeight(72);
        profileImageCtrl.setScaleType(Controls.ImageCtrl.ImageScaleType.CENTER_CROP);
        profileImageCtrl.setCornerRadius(36);
        profileImageCtrl.setElevation(6);
        profileImageCtrl.setImageUrl("http://www.aljanh.net/data/archive/img/268021457.jpeg");
        welcomeMessageEl.getControls().add(profileImageCtrl);

        Controls.PanelCtrl welcomeTextsEl = new Controls.PanelCtrl();
        welcomeTextsEl.setWidth(Controls.Control.MATCH_PARENT);
        welcomeTextsEl.setHeight(Controls.Control.MATCH_PARENT);
        welcomeTextsEl.setLayoutType(Controls.PanelCtrl.LayoutType.LINEAR_VERTICAL);
        welcomeTextsEl.setMarginLeft(80);
        welcomeTextsEl.setMarginTop(6);
        welcomeTextsEl.setMarginBottom(6);
        welcomeTextsEl.setControls(new ArrayList<>());
        welcomeMessageEl.getControls().add(welcomeTextsEl);

        Controls.TextCtrl profileTitleEl = new Controls.TextCtrl();
        profileTitleEl.setWidth(Controls.Control.WRAP_CONTENT);
        profileTitleEl.setHeight(32);
        profileTitleEl.setText("Keyhan Mohammadi");
        profileTitleEl.setTextColor(colorBlue);
        profileTitleEl.setTextSize(16);
        profileTitleEl.setGravityType(Controls.TextCtrl.GravityType.CENTER_VERTICAL);
        welcomeTextsEl.getControls().add(profileTitleEl);

        Controls.TextCtrl messageEl = new Controls.TextCtrl();
        messageEl.setWidth(Controls.Control.WRAP_CONTENT);
        messageEl.setHeight(28);
        messageEl.setText("Welcome to home in sky city");
        messageEl.setTextColor(colorBlue);
        messageEl.setTextSize(14);
        messageEl.setGravityType(Controls.TextCtrl.GravityType.CENTER_VERTICAL);
        welcomeTextsEl.getControls().add(messageEl);

        Controls.PanelCtrl clockEl = new Controls.PanelCtrl();
        clockEl.setWidth(300);
        clockEl.setHeight(300);
        clockEl.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        clockEl.setBackColor(colorDarkLight);
        clockEl.setCornerRadius(150);
        clockEl.setMarginTop(56);
        clockEl.setX(Controls.Control.CENTER);
        clockEl.setControls(new ArrayList<>());
        containerEl.getControls().add(clockEl);

        int x = 128;
        int y = 0;
        for (int counter = 0; counter < 12; counter++) {
            double cos = Math.cos(Math.toRadians(counter * 30 - 90));
            double sin = Math.sin(Math.toRadians(counter * 30 - 90));
            int posX = (int) (x * cos - y * sin) + 128 + 4;
            int posY = (int) (x * sin + y * cos) + 128 + 4;
            Controls.TextCtrl hourEl = new Controls.TextCtrl();
            hourEl.setX(posX);
            hourEl.setY(posY);
            hourEl.setWidth(32);
            hourEl.setHeight(32);
            hourEl.setText(counter == 0 ? "12" : counter + "");
            hourEl.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            hourEl.setTextColor(colorBlue);
            hourEl.setTextSize(18);
            clockEl.getControls().add(hourEl);
        }

        for (int counter = 0; counter < 60; counter++) {
            Controls.PanelCtrl panelCtrl = new Controls.PanelCtrl();
            panelCtrl.setWidth(2);
            panelCtrl.setHeight(300);
            panelCtrl.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
            panelCtrl.setRotation(counter * 6);
            panelCtrl.setX(150 - 4 / 2);
            clockEl.getControls().add(panelCtrl);

            Controls.PanelCtrl secondEl = new Controls.PanelCtrl();
            secondEl.setWidth(Controls.Control.MATCH_PARENT);
            secondEl.setHeight(8);
            secondEl.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
            secondEl.setBackColor(colorBlue);
            panelCtrl.getControls().add(secondEl);
        }

        Controls.PanelCtrl hourHand = new Controls.PanelCtrl();
        hourHand.setWidth(150);
        hourHand.setHeight(8);
        hourHand.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        hourHand.setX(Controls.Control.CENTER);
        hourHand.setY(clockEl.getHeight() / 2 - hourHand.getHeight() / 2);
        hourHand.setControls(new ArrayList<>());
        hourHand.setRotation(135);
        clockEl.getControls().add(hourHand);

        Controls.PanelCtrl hourHalfHand = new Controls.PanelCtrl();
        hourHalfHand.setWidth(hourHand.getWidth() / 2);
        hourHalfHand.setHeight(Controls.Control.MATCH_PARENT);
        hourHalfHand.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        hourHalfHand.setBackColor(colorBlue);
        hourHalfHand.setX(hourHand.getWidth() / 2);
        hourHalfHand.setControls(new ArrayList<>());
        hourHalfHand.setCornerRadius(4);
        hourHand.getControls().add(hourHalfHand);

        Controls.PanelCtrl minuteHand = new Controls.PanelCtrl();
        minuteHand.setWidth(200);
        minuteHand.setHeight(8);
        minuteHand.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        minuteHand.setX(Controls.Control.CENTER);
        minuteHand.setY(clockEl.getHeight() / 2 - minuteHand.getHeight() / 2);
        minuteHand.setControls(new ArrayList<>());
        clockEl.getControls().add(minuteHand);

        Controls.PanelCtrl minuteHalfHand = new Controls.PanelCtrl();
        minuteHalfHand.setWidth(minuteHand.getWidth() / 2);
        minuteHalfHand.setHeight(Controls.Control.MATCH_PARENT);
        minuteHalfHand.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        minuteHalfHand.setBackColor(colorBlue);
        minuteHalfHand.setX(minuteHand.getWidth() / 2);
        minuteHalfHand.setControls(new ArrayList<>());
        minuteHalfHand.setCornerRadius(4);
        minuteHand.getControls().add(minuteHalfHand);

        Controls.PanelCtrl calUp = new Controls.PanelCtrl();
        calUp.setWidth(300);
        calUp.setHeight(72);
        calUp.setX(Controls.Control.CENTER);
        calUp.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        calUp.setControls(new ArrayList<>());
        calUp.setMarginTop(56);
        calUp.setElevation(8);
        containerEl.getControls().add(calUp);

        Controls.PanelCtrl calUp1 = new Controls.PanelCtrl();
        calUp1.setWidth(16);
        calUp1.setHeight(72);
        calUp1.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        calUp1.setBackColor(colorBlue);
        calUp1.setX(56);
        calUp1.setCornerRadius(4);
        calUp1.setControls(new ArrayList<>());
        calUp.getControls().add(calUp1);

        Controls.PanelCtrl calUp2 = new Controls.PanelCtrl();
        calUp2.setWidth(16);
        calUp2.setHeight(112);
        calUp2.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        calUp2.setBackColor(colorBlue);
        calUp2.setX(calUp.getWidth() - 56 - calUp2.getWidth());
        calUp2.setCornerRadius(4);
        calUp2.setControls(new ArrayList<>());
        calUp.getControls().add(calUp2);

        Controls.PanelCtrl calBox = new Controls.PanelCtrl();
        calBox.setWidth(300);
        calBox.setHeight(328);
        calBox.setMarginTop(-36);
        calBox.setBackColor(colorDarkLight);
        calBox.setCornerRadius(16);
        calBox.setX(Controls.Control.CENTER);
        calBox.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        calBox.setControls(new ArrayList<>());
        containerEl.getControls().add(calBox);

        Controls.PanelCtrl innerCalBox = new Controls.PanelCtrl();
        innerCalBox.setWidth(280);
        innerCalBox.setHeight(Controls.Control.MATCH_PARENT);
        innerCalBox.setLayoutType(Controls.PanelCtrl.LayoutType.RELATIVE);
        innerCalBox.setControls(new ArrayList<>());
        innerCalBox.setMarginTop(40);
        innerCalBox.setX(Controls.Control.CENTER);
        calBox.getControls().add(innerCalBox);

        Controls.TextCtrl dateEl = new Controls.TextCtrl();
        dateEl.setWidth(Controls.Control.WRAP_CONTENT);
        dateEl.setHeight(Controls.Control.WRAP_CONTENT);
        dateEl.setText("January 2019");
        dateEl.setTextSize(20);
        dateEl.setTextColor(colorBlue);
        dateEl.setY(8);
        dateEl.setX(Controls.Control.CENTER);
        innerCalBox.getControls().add(dateEl);

        String[] dayNames = new String[]{
                "Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"
        };

        for (int counter = 0; counter < 7; counter++) {
            Controls.TextCtrl textCtrl = new Controls.TextCtrl();
            textCtrl.setText(dayNames[counter]);
            textCtrl.setWidth(40);
            textCtrl.setHeight(32);
            textCtrl.setTextColor(colorBlue);
            textCtrl.setTextSize(16);
            textCtrl.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            textCtrl.setX(counter * 40);
            textCtrl.setY(48);
            innerCalBox.getControls().add(textCtrl);
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
                Controls.TextCtrl textCtrl = new Controls.TextCtrl();
                textCtrl.setWidth(40);
                textCtrl.setHeight(32);
                textCtrl.setText(dayNums[counter * 7 + inCounter]);
                textCtrl.setGravityType(Controls.TextCtrl.GravityType.CENTER);
                if (counter == 0 && (inCounter == 0 || inCounter == 1))
                    textCtrl.setTextColor("#555555");
                else if (counter == 4 && (inCounter == 5 || inCounter == 6))
                    textCtrl.setTextColor("#555555");
                else if (counter == 5)
                    textCtrl.setTextColor("#555555");
                else
                    textCtrl.setTextColor(colorBlue);
                if (textCtrl.getText().equals("27")) {
                    textCtrl.setBorderColor(colorBlue);
                    textCtrl.setBorderWidth(2);
                    textCtrl.setCornerRadius(4);
                    textCtrl.setBackColor(colorDarkLight);
                }
                textCtrl.setTextSize(16);
                textCtrl.setX(inCounter * 40);
                textCtrl.setY(counter * 32 + 32 + 48);
                innerCalBox.getControls().add(textCtrl);
            }
        }

        Controls.LineChartCtrl lineChartCtrl = new Controls.LineChartCtrl();
        lineChartCtrl.setId("LineChartEl");
        lineChartCtrl.setWidth(300);
        lineChartCtrl.setHeight(260);
        lineChartCtrl.setLabelsColor(colorBlue);
        lineChartCtrl.setAxisColor(colorBlue);
        lineChartCtrl.setDotsColor(colorBlue);
        lineChartCtrl.setLineColor(colorBlue);
        lineChartCtrl.setLineThickness(2);
        lineChartCtrl.setDotsRadius(4);
        lineChartCtrl.setFillColor(colorDarkLight);

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

        lineChartCtrl.setPoints(points);
        lineChartCtrl.setX(Controls.Control.CENTER);
        lineChartCtrl.setMarginTop(56);
        containerEl.getControls().add(lineChartCtrl);

        Controls.VideoPlayerCtrl videoPlayerCtrl = new Controls.VideoPlayerCtrl();
        videoPlayerCtrl.setWidth(300);
        videoPlayerCtrl.setHeight(260);
        videoPlayerCtrl.setVideoUrl("https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4");
        videoPlayerCtrl.setX(Controls.Control.CENTER);
        videoPlayerCtrl.setMarginTop(56);
        containerEl.getControls().add(videoPlayerCtrl);

        Controls.DropDownCtrl dropDownCtrl = new Controls.DropDownCtrl();
        dropDownCtrl.setWidth(300);
        dropDownCtrl.setHeight(56);
        dropDownCtrl.setMarginTop(56);
        dropDownCtrl.setX(Controls.Control.CENTER);
        dropDownCtrl.setBackColor(colorDarkDark);
        dropDownCtrl.setCornerRadius(16);
        dropDownCtrl.setItems(new ArrayList<>());
        for (int counter = 0; counter < 5; counter++) {
            Controls.TextCtrl item = new Controls.TextCtrl();
            item.setWidth(Controls.Control.MATCH_PARENT);
            item.setHeight(56);
            item.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            item.setTextColor(colorBlue);
            item.setBackColor(colorDarkLight);
            item.setTextSize(18);
            item.setText("Item " + counter);
            dropDownCtrl.getItems().add(item);
        }
        Controls.ImageCtrl itemImage = new Controls.ImageCtrl();
        itemImage.setScaleType(Controls.ImageCtrl.ImageScaleType.CENTER_CROP);
        itemImage.setWidth(200);
        itemImage.setHeight(200);
        itemImage.setX(Controls.Control.CENTER);
        itemImage.setImageUrl("http://www.aljanh.net/data/archive/img/268021457.jpeg");
        dropDownCtrl.getItems().add(itemImage);
        for (int counter = 5; counter < 10; counter++) {
            Controls.TextCtrl item = new Controls.TextCtrl();
            item.setWidth(Controls.Control.MATCH_PARENT);
            item.setHeight(56);
            item.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            item.setTextColor(colorBlue);
            item.setBackColor(colorDarkLight);
            item.setTextSize(18);
            item.setText("Item " + counter);
            dropDownCtrl.getItems().add(item);
        }

        containerEl.getControls().add(dropDownCtrl);

        Controls.CheckCtrl checkCtrl = new Controls.CheckCtrl();
        checkCtrl.setCaption("Keyhan");
        checkCtrl.setWidth(Controls.Control.WRAP_CONTENT);
        checkCtrl.setHeight(Controls.Control.WRAP_CONTENT);
        checkCtrl.setX(Controls.Control.CENTER);
        checkCtrl.setCaptionColor(colorBlue);
        checkCtrl.setMarginTop(56);
        checkCtrl.setTintColor(colorBlue);
        containerEl.getControls().add(checkCtrl);

        Controls.OptionCtrl optionCtrl = new Controls.OptionCtrl();
        optionCtrl.setCaption("Keyhan");
        optionCtrl.setWidth(Controls.Control.WRAP_CONTENT);
        optionCtrl.setHeight(Controls.Control.WRAP_CONTENT);
        optionCtrl.setX(Controls.Control.CENTER);
        optionCtrl.setCaptionColor(colorBlue);
        optionCtrl.setMarginTop(56);
        optionCtrl.setTintColor(colorBlue);
        containerEl.getControls().add(optionCtrl);

        Controls.RecyclerCtrl recyclerCtrl = new Controls.RecyclerCtrl();
        recyclerCtrl.setWidth(300);
        recyclerCtrl.setHeight(400);
        recyclerCtrl.setMarginTop(56);
        recyclerCtrl.setMarginBottom(300);
        recyclerCtrl.setX(Controls.Control.CENTER);
        recyclerCtrl.setBackColor(colorDarkDark);
        recyclerCtrl.setCornerRadius(16);
        recyclerCtrl.setOrientation(Controls.RecyclerCtrl.RecyclerOrientation.VERTICAL);
        recyclerCtrl.setRecyclerType(Controls.RecyclerCtrl.RecyclerLayoutType.GRID);
        recyclerCtrl.setGridSpanCount(1);
        recyclerCtrl.setItems(new ArrayList<>());
        for (int counter = 0; counter < 5; counter++) {
            Controls.TextCtrl item = new Controls.TextCtrl();
            item.setWidth(Controls.Control.MATCH_PARENT);
            item.setHeight(56);
            item.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            item.setTextColor(colorBlue);
            item.setBackColor(colorDarkLight);
            item.setTextSize(18);
            item.setText("Item " + counter);
            recyclerCtrl.getItems().add(item);
        }
        Controls.ImageCtrl rItemImage = new Controls.ImageCtrl();
        rItemImage.setScaleType(Controls.ImageCtrl.ImageScaleType.CENTER_CROP);
        rItemImage.setWidth(200);
        rItemImage.setHeight(200);
        rItemImage.setX(Controls.Control.CENTER);
        rItemImage.setImageUrl("http://www.aljanh.net/data/archive/img/268021457.jpeg");
        recyclerCtrl.getItems().add(rItemImage);
        for (int counter = 5; counter < 10; counter++) {
            Controls.TextCtrl item = new Controls.TextCtrl();
            item.setWidth(Controls.Control.MATCH_PARENT);
            item.setHeight(56);
            item.setGravityType(Controls.TextCtrl.GravityType.CENTER);
            item.setTextColor(colorBlue);
            item.setBackColor(colorDarkLight);
            item.setTextSize(18);
            item.setText("Item " + counter);
            recyclerCtrl.getItems().add(item);
        }
        containerEl.getControls().add(recyclerCtrl);

        Controls.RecyclerCtrl hrc = new Controls.RecyclerCtrl();
        hrc.setWidth(Controls.Control.MATCH_PARENT);
        hrc.setHeight(224);
        hrc.setRecyclerType(Controls.RecyclerCtrl.RecyclerLayoutType.LINEAR);
        hrc.setOrientation(Controls.RecyclerCtrl.RecyclerOrientation.HORIZONTAL);
        hrc.setItems(new ArrayList<>());
        for (int counter = 0; counter < 10; counter++) {
            Controls.ImageCtrl imageCtrl = new Controls.ImageCtrl();
            imageCtrl.setId("Image" + counter);
            imageCtrl.setWidth(224);
            imageCtrl.setHeight(224);
            imageCtrl.setScaleType(Controls.ImageCtrl.ImageScaleType.CENTER_CROP);
            imageCtrl.setImageUrl("http://www.aljanh.net/data/archive/img/268021457.jpeg");
            hrc.getItems().add(imageCtrl);
        }
        recyclerCtrl.getItems().add(hrc);

        PulseView pulseView = findViewById(R.id.pulseView);
        pulseView.setup(this);

        try {
            String json = new ObjectMapper().writeValueAsString(scrollerCtrl);
            Log.d("Pulse", json);
            pulseView.buildUi(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        new Handler().postDelayed(() -> {
            Updates.ControlUpdateMarginTop updateMarginTop = new Updates.ControlUpdateMarginTop();
            updateMarginTop.setControlId("WelcomeMessageEl");
            updateMarginTop.setValue(224);
            pulseView.updateUi(updateMarginTop);

            new Handler().postDelayed(() -> {
                Updates.ControlUpdateHeight updateHeight = new Updates.ControlUpdateHeight();
                updateHeight.setControlId("WelcomeMessageEl");
                updateHeight.setValue(200);
                pulseView.updateUi(updateHeight);

                new Handler().postDelayed(() -> {
                    Updates.LineChartCtrlUpdateLineColor updateLineColor = new Updates.LineChartCtrlUpdateLineColor();
                    updateLineColor.setControlId("LineChartEl");
                    updateLineColor.setValue("#00ff00");
                    pulseView.updateUi(updateLineColor);
                }, 3000);
            }, 3000);
        }, 3000);
    }
}

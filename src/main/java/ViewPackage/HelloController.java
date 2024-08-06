package ViewPackage;

import ViewModelPackage.ViewContract;
import ViewModelPackage.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

public class HelloController implements Initializable, ViewContract {
    private String[] channelNames = {"channel 1", "channel 2", "channel 3", "channel 4", "channel 5", "channel 6", "channel 7", "channel 8"};
    private int currentSet = -1;

    @FXML
    private Button channel1Button;

    @FXML
    private Button channel3Button;

    @FXML
    private Button channel2Button;

    @FXML
    private Button channel4Button;

    @FXML
    private Button mute1Btn;

    @FXML
    private Button mute2Btn;

    @FXML
    private Button mute3Btn;

    @FXML
    private Button mute4Btn;

    @FXML
    private Button up1Btn;

    @FXML
    private Button up2Btn;

    @FXML
    private Button up3Btn;

    @FXML
    private Button up4Btn;

    @FXML
    private Button down1Btn;

    @FXML
    private Button down2Btn;

    @FXML
    private Button down3Btn;

    @FXML
    private Button down4Btn;

    @FXML
    private Button shiftBtn;

    @FXML
    private Label chn1NameLbl;

    @FXML
    private Label chn2NameLbl;

    @FXML
    private Label chn3NameLbl;

    @FXML
    private Label chn4NameLbl;

    @FXML
    private ProgressBar chn1Pbar;

    @FXML
    private ProgressBar chn2Pbar;

    @FXML
    private ProgressBar chn3Pbar;

    @FXML
    private ProgressBar chn4Pbar;

    @FXML
    private HBox chn1Panel;

    @FXML
    private HBox chn2Panel;

    @FXML
    private HBox chn3Panel;

    @FXML
    private HBox chn4Panel;

    @FXML
    private Slider commonVolumeSlider;

    private ImageView iconUnmutedView1;
    private ImageView iconUnmutedView2;
    private ImageView iconUnmutedView3;
    private ImageView iconUnmutedView4;

    private ImageView iconMutedView1;
    private ImageView iconMutedView2;
    private ImageView iconMutedView3;
    private ImageView iconMutedView4;
    private ViewModel viewModel;

    @FXML
    void onMute1(ActionEvent event) {
        System.err.println("onMute1 clicked");
        viewModel.muteChannel(0);
    }

    @FXML
    void onMute2(ActionEvent event) {
        System.err.println("onMute2 clicked");
        viewModel.muteChannel(1);
    }

    @FXML
    void onMute3(ActionEvent event) {
        System.err.println("onMute3 clicked");
        viewModel.muteChannel(2);
    }

    @FXML
    void onMute4(ActionEvent event) {
        System.err.println("onMute4 clicked");
        viewModel.muteChannel(3);
    }

    @FXML
    void onShift(ActionEvent event) {
        System.err.println("shift clicked: " + currentSet);
        viewModel.shiftChannels();
    }

    @FXML
    void onUp1Btn(ActionEvent event) {
        System.out.println("btn 1 up");
        viewModel.raiseVolume(0);
        viewModel.raiseVolume(0);
        viewModel.raiseVolume(0);
    }

    @FXML
    void onUp2Btn(ActionEvent event) {
        System.out.println("btn 2 up");
        viewModel.raiseVolume(1);
        viewModel.raiseVolume(1);
        viewModel.raiseVolume(1);
    }

    @FXML
    void onUp3Btn(ActionEvent event) {
        System.out.println("btn 3 up");
        viewModel.raiseVolume(2);
        viewModel.raiseVolume(2);
        viewModel.raiseVolume(2);
    }

    @FXML
    void onUp4Btn(ActionEvent event) {
        System.out.println("btn 4 up");
        viewModel.raiseVolume(3);
    }

    @FXML
    void onDown1Btn(ActionEvent event) {
        System.out.println("btn 1 down");
        viewModel.lowerVolume(0);
    }

    @FXML
    void onDown2Btn(ActionEvent event) {
        System.out.println("btn 2 down");
        viewModel.lowerVolume(1);
    }

    @FXML
    void onDown3Btn(ActionEvent event) {
        System.out.println("btn 3 down");
        viewModel.lowerVolume(2);
    }

    @FXML
    void onDown4Btn(ActionEvent event) {
        System.out.println("btn 4 down");
        viewModel.lowerVolume(3);
    }

    public void setChannelName(int channel, String name, int currentSet) {
        System.out.println("setting name");
        switch (channel) {
            case 0:
                channelNames[0] = name;
                break;
            case 1:
                channelNames[1] = name;
                break;
            case 2:
                channelNames[2] = name;
                break;
            case 3:
                channelNames[3] = name;
                break;
            case -128:
                channelNames[4] = name;
                break;
            case -127:
                channelNames[5] = name;
                break;
            case -126:
                channelNames[6] = name;
                break;
            case -125:
                channelNames[7] = name;
                break;
        }
        this.currentSet = currentSet;
        setNames(currentSet);
    }

    private void setNames(int set) {
        System.err.println("setNames: " + set);
        if (set == 0){
            chn1NameLbl.setText(channelNames[0]);
            chn2NameLbl.setText(channelNames[1]);
            chn3NameLbl.setText(channelNames[2]);
            chn4NameLbl.setText(channelNames[3]);
            currentSet = 0;
        } else
        if (set == 1){
            chn1NameLbl.setText(channelNames[4]);
            chn2NameLbl.setText(channelNames[5]);
            chn3NameLbl.setText(channelNames[6]);
            chn4NameLbl.setText(channelNames[7]);
            currentSet = 1;
        }
        else
            System.err.println("Something went wrong in setNames(), argument set wasn't 0 or 1: " + set);
    }

    public void setChannelVolume(int channel, int volume, int color) {
        System.out.println("set volume: " + volume + " on challen: " + channel);

        switch (channel) {
            case 0:
                chn1Pbar.setProgress(0.125F * (volume + 1));
                setColor(color, chn1Panel, mute1Btn, chn1Pbar);
                if (color == 0 || color == 1){
                    setMuteIconView(iconUnmutedView1, mute1Btn, false);
                }
                if (color == -1 || color == 2){
                    setMuteIconView(iconMutedView1, mute1Btn, true);
                }
                break;
            case 1:
                chn2Pbar.setProgress(0.125F * (volume + 1));
                setColor(color, chn2Panel, mute2Btn, chn2Pbar);
                if (color == 0 || color == 1){
                    setMuteIconView(iconUnmutedView2, mute2Btn, false);
                }
                if (color == -1 || color == 2){
                    setMuteIconView(iconMutedView2, mute2Btn, true);
                }
                break;
            case 2:
                chn3Pbar.setProgress(0.125F * (volume + 1));
                setColor(color, chn3Panel, mute3Btn, chn3Pbar);
                if (color == 0 || color == 1){
                    setMuteIconView(iconUnmutedView3, mute3Btn, false);
                }
                if (color == -1 || color == 2){
                    setMuteIconView(iconMutedView3, mute3Btn, true);
                }
                break;
            case 3:
                chn4Pbar.setProgress(0.125F * (volume + 1));
                setColor(color, chn4Panel, mute4Btn, chn4Pbar);
                if (color == 0 || color == 1){
                    setMuteIconView(iconUnmutedView4, mute4Btn, false);
                }
                if (color == -1 || color == 2){
                    setMuteIconView(iconMutedView4, mute4Btn, true);
                }
                break;
        }
    }

    /*
     * 0 - yellow bar no border
     * 1 - yellow bar greenborder
     * 2 - red bar green border
     * -1 - red bar no green border
     */
    private void setColor(int color, HBox panel, Button muteButton, ProgressBar pbar) {
        System.err.println("color code: " + color);
        if (color == 0){
            pbar.getStyleClass().removeAll("red-bar");
            pbar.getStyleClass().add("yellow-bar");

            panel.getStyleClass().removeAll("green-border");
//            muteButton.setTextFill(Color.valueOf("black"));
        }
            else
            if(color == 1){
                pbar.getStyleClass().removeAll("red-bar");
                pbar.getStyleClass().add("yellow-bar");

                panel.getStyleClass().removeAll("green-border");
                panel.getStyleClass().add("green-border");
//                muteButton.setTextFill(Color.valueOf("black"));
            }
                else
                if(color == -1){
                    panel.getStyleClass().removeAll("green-border");

                    pbar.getStyleClass().removeAll("yellow-bar");
                    pbar.getStyleClass().add("red-bar");
//                    muteButton.setTextFill(Color.valueOf("red"));
                }
                    else
                    if ((color == 2)){
                        panel.getStyleClass().removeAll("green-border");
                        panel.getStyleClass().add("green-border");

                        pbar.getStyleClass().removeAll("yellow-bar");
                        pbar.getStyleClass().add("red-bar");
//                        muteButton.setTextFill(Color.valueOf("red"));
                    }
    }

    /*
     * 0 - red bar
     * 1 - green bar
     */
    private void setBarColor(int color, ProgressBar pbar){
        if (color == 0){
            pbar.getStyleClass().removeAll("yellow-bar");
            pbar.getStyleClass().add("red-bar");
        }
        else if (color == 1){
            pbar.getStyleClass().removeAll("red-bar");
            pbar.getStyleClass().add("yellow-bar");
        }
    }

    /*
     * 0 - no border
     * 1 - green border
     */
    private void setBorderColor(int color, HBox panel){
        if(color == 0){
            panel.getStyleClass().removeAll("green-border");
        }
        else if (color == 1){
            panel.getStyleClass().removeAll("green-border");
            panel.getStyleClass().add("green-border");

        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.err.println("loc: " + location + " res: " + resources);
        Image micIcon = new Image(getClass().getClassLoader().getResource("icons/bytesize_microphonewhite.png").toExternalForm());
        createButtonIcon(micIcon, channel1Button, 30);
        createButtonIcon(micIcon, channel2Button, 30);
        createButtonIcon(micIcon, channel3Button, 30);
        createButtonIcon(micIcon, channel4Button, 30);

        Image plusIcon = new Image(getClass().getClassLoader().getResource("icons/tabler_pluswhite.png").toExternalForm());
        createButtonIcon(plusIcon, up1Btn, 20);
        createButtonIcon(plusIcon, up2Btn, 20);
        createButtonIcon(plusIcon, up3Btn, 20);
        createButtonIcon(plusIcon, up4Btn, 20);

        Image minusIcon = new Image(getClass().getClassLoader().getResource("icons/tabler_minuswhite.png").toExternalForm());
        createButtonIcon(minusIcon, down1Btn, 20);
        createButtonIcon(minusIcon, down2Btn, 20);
        createButtonIcon(minusIcon, down3Btn, 20);
        createButtonIcon(minusIcon, down4Btn, 20);

        Image muteIconUnmuted = new Image(getClass().getClassLoader().getResource("icons/ph_speaker-highwhite.png").toExternalForm());
        Image muteIconMuted = new Image(getClass().getClassLoader().getResource("icons/ph_speaker-xred.png").toExternalForm());

        iconUnmutedView1 = new ImageView(muteIconUnmuted);
        iconMutedView1 = new ImageView(muteIconMuted);
        setMuteIconView(iconUnmutedView1, mute1Btn, false);

        iconUnmutedView2 = new ImageView(muteIconUnmuted);
        iconMutedView2 = new ImageView(muteIconMuted);
        setMuteIconView(iconUnmutedView2, mute2Btn, false);

        iconUnmutedView3 = new ImageView(muteIconUnmuted);
        iconMutedView3 = new ImageView(muteIconMuted);
        setMuteIconView(iconUnmutedView3, mute3Btn, false);

        iconUnmutedView4 = new ImageView(muteIconUnmuted);
        iconMutedView4 = new ImageView(muteIconMuted);
        setMuteIconView(iconUnmutedView4, mute4Btn, false);

//        StackPane sliderTrackPane = (StackPane) commonVolumeSlider.lookup(".track");

        commonVolumeSlider.setStyle("-track-color: #14a050");
        commonVolumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                viewModel.setCommonVolume(newValue.intValue());

                double percentage = (100.0 * newValue.doubleValue() / commonVolumeSlider.getMax()) / 100;
//                String style = String.format("-fx-background-color: linear-gradient(to top, #14a050 %1$.1f%%, #969696 %1$.1f%%);", percentage);
//                String style = "-track-color: red;";
                System.out.println("value = " + newValue.doubleValue() / 100);
                DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
                decimalFormatSymbols.setDecimalSeparator('.');
                DecimalFormat decimalFormat = new DecimalFormat("#0.00", decimalFormatSymbols);
//                String style = String.format("-track-color: linear-gradient(to top, " +
//                        "#14a050 0.0, " +
//                        "#14a050 " + decimalFormat.format(percentage) + ", " +
//                        "-default-track-color " + decimalFormat.format(percentage) + ", " +
//                        "-default-track-color 1.0);",
//                        percentage);
//                String style = String.format("-track-color: linear-gradient(to top, #14a050 0.5, #969696 0.5);",
//                String style = String.format("-track-color: linear-gradient(to top, #14a050 " + decimalFormat.format(percentage) + ", #969696 " + decimalFormat.format(percentage) + ");");
//                linear-gradient(to top, derive(-fx-text-box-border, -10%), -fx-text-box-border), -fx-background-color: -track-color;
                String style = "-track-color: linear-gradient(to top, #14a050 " + decimalFormat.format(percentage) + ", #969696 " + decimalFormat.format(percentage) + ");";
                System.out.println(style);
                commonVolumeSlider.setStyle(style);
            }
        });

        viewModel = new ViewModel(this);
//        viewModel.setCommonVolume((int) commonVolumeSlider.getValue());
    }

    private void setMuteIconView(ImageView view, Button btn, Boolean muted){
        view.setFitWidth(20);
        view.setPreserveRatio(true);
//        if (muted){
//            ColorAdjust monochrome = new ColorAdjust();
////            monochrome.setHue(0);
////            monochrome.setSaturation(100);
////            monochrome.setBrightness(50);
//
//
//            Blend paintRed = new Blend(
//                    BlendMode.MULTIPLY,
//                    monochrome,
//                    new ColorInput(
//                            0,
//                            0,
//                            20,
//                            20,
//                            Color.RED
//                    )
//            );
//
//            view.setEffect((Effect) paintRed);
//        }
//        else
//            view.setEffect(null);

        btn.setGraphic(view);
    }

    private void createButtonIcon(Image img, Button button, double width) {
        ImageView view = new ImageView(img);
        view.setFitWidth(width);
        view.setPreserveRatio(true);
        button.setGraphic(view);
    }

    @Override
    public void showChannelName(int channel, String name) {
        switch (channel){
            case 0:
                chn1NameLbl.setText(name);
                break;
            case 1:
                chn2NameLbl.setText(name);
                break;
            case 2:
                chn3NameLbl.setText(name);
                break;
            case 3:
                chn4NameLbl.setText(name);
                break;
        }
    }

    @Override
    public void showChannelActive(int channel) {
        switch (channel) {
            case 0:
                setBorderColor(1, chn1Panel);
                break;
            case 1:
                setBorderColor(1, chn2Panel);
                break;
            case 2:
                setBorderColor(1, chn3Panel);
                break;
            case 3:
                setBorderColor(1, chn4Panel);
                break;
        }
    }

    @Override
    public void showChannelInactive(int channel) {
        switch (channel) {
            case 0:
                setBorderColor(0, chn1Panel);
                break;
            case 1:
                setBorderColor(0, chn2Panel);
                break;
            case 2:
                setBorderColor(0, chn3Panel);
                break;
            case 3:
                setBorderColor(0, chn4Panel);
                break;
        }
    }

    @Override
    public void showChannelVolume(int channel, int level) {
        switch (channel) {
            case 0:
                chn1Pbar.setProgress(0.125F * (level + 1));
                break;
            case 1:
                chn2Pbar.setProgress(0.125F * (level + 1));
                break;
            case 2:
                chn3Pbar.setProgress(0.125F * (level + 1));
                break;
            case 3:
                chn4Pbar.setProgress(0.125F * (level + 1));
                break;
        }
    }

    @Override
    public void showChannelMute(int channel, boolean muted) {
        switch (channel) {
            case 0:
                if (muted) {
                    setBarColor(0, chn1Pbar);
                    setMuteIconView(iconMutedView1, mute1Btn, true);
                }
                else {
                    setBarColor(1, chn1Pbar);
                    setMuteIconView(iconUnmutedView1, mute1Btn, false);
                }
//                }
                break;
            case 1:
                if (muted) {
                    setBarColor(0, chn2Pbar);
                    setMuteIconView(iconMutedView2, mute2Btn, true);
                }
                else {
                    setBarColor(1, chn2Pbar);
                    setMuteIconView(iconUnmutedView2, mute2Btn, false);
                }
                break;
            case 2:
                if (muted) {
                    setBarColor(0, chn3Pbar);
                    setMuteIconView(iconMutedView3, mute3Btn, true);
                }
                else {
                    setBarColor(1, chn3Pbar);
                    setMuteIconView(iconUnmutedView3, mute3Btn, false);
                }
                break;
            case 3:
                if (muted) {
                    setBarColor(0, chn4Pbar);
                    setMuteIconView(iconMutedView4, mute4Btn, true);
                }
                else {
                    setBarColor(1, chn4Pbar);
                    setMuteIconView(iconUnmutedView4, mute4Btn, false);
                }
                break;
        }
    }

    public void stop() {
        viewModel.stop();
    }
}

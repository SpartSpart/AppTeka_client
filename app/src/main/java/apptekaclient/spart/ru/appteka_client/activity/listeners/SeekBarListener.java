package apptekaclient.spart.ru.appteka_client.activity.listeners;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Pamela on 17.11.2019.
 */

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar;
    private TextView textView;

    public SeekBarListener(SeekBar seekBar, TextView textView) {
        this.seekBar = seekBar;
        this.textView = textView;
    }

    @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            textView.setText(String.valueOf(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }



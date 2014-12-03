package org.ligi.scr;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Collection;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import info.metadude.java.library.halfnarp.model.GetTalksResponse;

public class EventViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.titleTV)
    TextView titleText;

    @InjectView(R.id.abstractTV)
    TextView abstractText;

    @InjectView(R.id.speaker)
    TextView speakerText;

    @InjectView(R.id.track)
    TextView trackText;

    @InjectView(R.id.talkSwitch)
    SwitchCompat talkSwitch;

    @InjectView(R.id.shareView)
    View shareView;

    private CardView root;

    public EventViewHolder(CardView itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
        this.root = itemView;
    }

    public void apply(final GetTalksResponse response) {
        titleText.setText(response.getTitle());
        abstractText.setText(response.getAbstract());
        speakerText.setText(response.getSpeakers());
        trackText.setText(response.getTrackName());

        talkSwitch.setChecked(App.talkIds.getTalkIds().contains(response.getEventId()));

        talkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final Collection<Integer> talkIds = App.talkIds.getTalkIds();
                if (isChecked) {
                    talkIds.add(response.getEventId());
                } else {
                    talkIds.remove(response.getEventId());
                }
                App.talkIds.clear();
                App.talkIds.add(talkIds);
                App.talkIds.save();
            }
        });

        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT,response.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT, response.getAbstract());
                intent.setType("text/plain");
                root.getContext().startActivity(intent);
            }
        });

    }

}
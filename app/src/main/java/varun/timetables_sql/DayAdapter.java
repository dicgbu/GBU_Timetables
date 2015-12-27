package varun.timetables_sql;

import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import varun.timetables_sql.R;

/**
 * Created by varun on 12/23/15.
 */
public class DayAdapter extends ArrayAdapter<Integer> {

    int Section;
    Context context;

    public interface RecycleListener {

        public void onScroll(int scroll_x,int adapter_position);
    }
    private RecycleListener listener;

    public DayAdapter(Context context, ArrayList<Integer> Days, int Section) {
        super(context, 0, Days);
        this.listener = null;

        this.Section = Section;
        this.context = context;
    }

    public void setRecycleListener(RecycleListener listener)
    {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int day_no = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timetable_row, parent, false);
        }
        ArrayList<Integer> periods = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            periods.add(i);
        }

        final RecyclerView mRecyclerView = (RecyclerView) convertView.findViewById(R.id.timetable_row_recycler);

        final RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        TimetableAdapter timetableAdapter = new TimetableAdapter(context, periods, Section, day_no);
        mRecyclerView.setAdapter(timetableAdapter);


        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int overallXScroll = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                listener.onScroll(recyclerView.computeHorizontalScrollOffset(),position);

            }
        });
        return convertView;
    }
}
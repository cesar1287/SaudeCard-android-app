package comcesar1287.github.www.saudecard.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.adapter.PartnerAdapter;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.interfaces.RecyclerViewOnClickListenerHack;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import comcesar1287.github.www.saudecard.view.FavoriteActivity;
import comcesar1287.github.www.saudecard.view.PartnerCategoryActivity;
import comcesar1287.github.www.saudecard.view.PartnerDetailsActivity;

public class PartnerFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    RecyclerView mRecyclerView;
    public List<Partner> mList;
    public PartnerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        if(getActivity() instanceof PartnerCategoryActivity) {
            mList = ((PartnerCategoryActivity) getActivity()).getPartnersList();
        }else if(getActivity() instanceof FavoriteActivity){
            mList = ((FavoriteActivity) getActivity()).getPartnersList();
        }
        adapter = new PartnerAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter( adapter );

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener( getActivity(), mRecyclerView, this ));

        return view;

    }

    @Override
    public void onClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), PartnerDetailsActivity.class);
        intent.putExtra(Utility.KEY_CONTENT_EXTRA_PARTNER, mList.get(position));
        startActivity(intent);
    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
        private Context mContext;
        private GestureDetector mGestureDetector;
        private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

        RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvoclh){
            mContext = c;
            mRecyclerViewOnClickListenerHack = rvoclh;

            mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && mRecyclerViewOnClickListenerHack != null){
                        mRecyclerViewOnClickListenerHack.onClickListener(cv,
                                rv.getChildAdapterPosition(cv) );
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            mGestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }
}

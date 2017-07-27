package comcesar1287.github.www.saudecard.controller.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.adapter.CategorieAdapter;
import comcesar1287.github.www.saudecard.controller.domain.Categorie;
import comcesar1287.github.www.saudecard.controller.interfaces.RecyclerViewOnClickListenerHack;
import comcesar1287.github.www.saudecard.view.MainActivity;

public class CategorieFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    RecyclerView mRecyclerView;
    public List<Categorie> mList;
    public CategorieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorie, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvCategories);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mList = ((MainActivity) getActivity()).getCategoriesList();
        adapter = new CategorieAdapter(getActivity(), mList);

        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter( adapter );

        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener( getActivity(), mRecyclerView, this ));

        return view;

    }

    @Override
    public void onClickListener(View view, int position) {
        /*Intent intent = new Intent(getActivity(), SegmentCategoryActivity.class);
        intent.putExtra(Utility.SEGMENTO, mList.get(position).getName());
        intent.putExtra(Utility.SEGMENTO_FIREBASE, mList.get(position).getNameFirebase());
        startActivity(intent);*/
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

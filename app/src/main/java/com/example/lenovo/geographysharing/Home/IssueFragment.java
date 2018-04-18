package com.example.lenovo.geographysharing.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.geographysharing.BaseClass.BaseFragment;
import com.example.lenovo.geographysharing.EntityClass.Issue;
import com.example.lenovo.geographysharing.Issue.IssueDetailActivity;
import com.example.lenovo.geographysharing.R;

import static com.example.lenovo.geographysharing.EntityClass.Issue.IDEZHI;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IDIZHISHIYAN;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IHUATAN;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IMAX_COUNT;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IMEIYOU;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IQITA;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IRENYUAN;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IRUANJIAN;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IWUTAN;
import static com.example.lenovo.geographysharing.EntityClass.Issue.IZUANTAN;

/**
 * Created by lenovo on 2017/12/31.
 */

public class IssueFragment extends BaseFragment {
    private GridView mgridView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_issue;
    }

    @Override
    protected void initView() {
        mgridView = bindViewId(R.id.gv_issue);
        mgridView.setAdapter(new IssueAdapter());
        mgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                switch (position){
                    default:
                        IssueDetailActivity.launchIssueDetailActivity(getActivity());
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    class IssueAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAX_COUNT;
        }

        @Override
        public Issue getItem(int position) {
            return new Issue(position+1,getActivity());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            Issue issue = getItem(position);
            IssueFragment.ViewHolder holder = null;
            if (view == null) {

                view = LayoutInflater.from(getActivity()).inflate(R.layout.issue_gird_item,null);
                holder = new ViewHolder();
                holder.textView =(TextView)view.findViewById(R.id.tv_issue_item_text);
                holder.imageView = (ImageView)view.findViewById(R.id.iv_issue_item_image);
                view.setTag(holder);
            }else {
                holder = (IssueFragment.ViewHolder)view.getTag();
            }

            holder.textView.setText(issue.getissueName());

            int id  = issue.getissueId();
            int imgResId = -1;
            switch (id){
                case IDEZHI:
                    imgResId = R.drawable.ic_show;
                    break;
                case IWUTAN:
                    imgResId = R.drawable.ic_movie;
                    break;
                case IHUATAN:
                    imgResId = R.drawable.ic_comic;
                    break;
                case IZUANTAN:
                    imgResId = R.drawable.ic_movie;
                    break;
                case IDIZHISHIYAN:
                    imgResId = R.drawable.ic_music;
                    break;
                case IRENYUAN:
                    imgResId = R.drawable.ic_variety;
                    break;
                case IRUANJIAN:
                    imgResId = R.drawable.ic_live;
                    break;
                case IQITA:
                    imgResId = R.drawable.ic_bookmark;
                    break;
                case IMEIYOU:
                    imgResId = R.drawable.ic_history;
                    break;
            }
            holder.imageView.setImageDrawable(getActivity().getResources().getDrawable(imgResId));
            return view;
        }
    }
    class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}

package wannabit.io.cosmostaion.activities.chains.osmosis;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.base.BaseActivity;
import wannabit.io.cosmostaion.base.BaseChain;
import wannabit.io.cosmostaion.base.BaseFragment;
import wannabit.io.cosmostaion.fragment.chains.osmosis.ListFarmingFragment;
import wannabit.io.cosmostaion.fragment.chains.osmosis.ListPoolFragment;
import wannabit.io.cosmostaion.fragment.chains.osmosis.ListSwapFragment;
import wannabit.io.cosmostaion.task.TaskListener;
import wannabit.io.cosmostaion.task.TaskResult;
import wannabit.io.cosmostaion.utils.WDp;

public class LabsListActivity extends BaseActivity implements TaskListener {

    private Toolbar mToolbar;
    private ViewPager mLabPager;
    private TabLayout mLabTapLayer;
    private OsmoLabPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs_list);
        mToolbar = findViewById(R.id.tool_bar);
        mLabTapLayer = findViewById(R.id.lab_tab);
        mLabPager = findViewById(R.id.lab_view_pager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAccount = getBaseDao().onSelectAccount(getBaseDao().getLastUser());
        mBaseChain = BaseChain.getChain(mAccount.baseChain);

        mPageAdapter = new LabsListActivity.OsmoLabPageAdapter(getSupportFragmentManager());
        mLabPager.setAdapter(mPageAdapter);
        mLabTapLayer.setupWithViewPager(mLabPager);
        mLabTapLayer.setTabRippleColor(null);

        View tab0 = LayoutInflater.from(this).inflate(R.layout.view_tab_myvalidator, null);
        TextView tabItemText0 = tab0.findViewById(R.id.tabItemText);
        tabItemText0.setText(R.string.str_osmosis_swap);
        tabItemText0.setTextColor(WDp.getTabColor(this, mBaseChain));
        mLabTapLayer.getTabAt(0).setCustomView(tab0);

        View tab1 = LayoutInflater.from(this).inflate(R.layout.view_tab_myvalidator, null);
        TextView tabItemText1 = tab1.findViewById(R.id.tabItemText);
        tabItemText1.setTextColor(WDp.getTabColor(this, mBaseChain));
        tabItemText1.setText(R.string.str_osmosis_pool_list);
        mLabTapLayer.getTabAt(1).setCustomView(tab1);

        View tab2 = LayoutInflater.from(this).inflate(R.layout.view_tab_myvalidator, null);
        TextView tabItemText2 = tab2.findViewById(R.id.tabItemText);
        tabItemText2.setTextColor(WDp.getTabColor(this, mBaseChain));
        tabItemText2.setText(R.string.str_osmosis_farming);
        mLabTapLayer.getTabAt(2).setCustomView(tab2);

        mLabPager.setOffscreenPageLimit(2);
        mLabPager.setCurrentItem(0, false);

        mLabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }
            @Override
            public void onPageScrollStateChanged(int i) { }
            @Override
            public void onPageSelected(int i) {
                mPageAdapter.mFragments.get(i).onRefreshTab();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTaskResponse(TaskResult result) {
        super.onTaskResponse(result);
    }

    private class OsmoLabPageAdapter extends FragmentPagerAdapter {
        private ArrayList<BaseFragment> mFragments = new ArrayList<>();
        private BaseFragment mCurrentFragment;
        public OsmoLabPageAdapter(FragmentManager fm) {
            super(fm);
            mFragments.clear();
            mFragments.add(ListSwapFragment.newInstance(null));
            mFragments.add(ListPoolFragment.newInstance(null));
            mFragments.add(ListFarmingFragment.newInstance(null));
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((BaseFragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        public BaseFragment getCurrentFragment() {
            return mCurrentFragment;
        }

        public ArrayList<BaseFragment> getFragments() {
            return mFragments;
        }
    }

}
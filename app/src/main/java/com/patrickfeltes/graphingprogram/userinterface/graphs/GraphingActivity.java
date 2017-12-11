package com.patrickfeltes.graphingprogram.userinterface.graphs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;

/**
 * GraphingActivity handles equation input, graphing of functions, and sharing with other users
 */
public class GraphingActivity extends AuthenticatedActivity {

    private Fragment graphingFragment;
    private Fragment equationFragment;

    // manifest is set to not recreate on orientation change
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String graphKey = (String) getIntent().getExtras().get(ExtraKeys.GRAPH_KEY);

        graphingFragment = new GraphingFragment();
        equationFragment = new EquationFragment();

        // send graph info to the fragments
        Bundle bundle = new Bundle();
        bundle.putString(ExtraKeys.GRAPH_KEY, graphKey);
        graphingFragment.setArguments(bundle);
        equationFragment.setArguments(bundle);

        // set the initial fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, equationFragment);
        ft.commit();

        setContentView(R.layout.activity_graphing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_equations:
                ft.replace(R.id.fragment_container, equationFragment);
                ft.commit();
                return true;
            case R.id.action_graph:
                ft.replace(R.id.fragment_container, graphingFragment);
                ft.commit();
                return true;
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.objects.EquationList;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;

import java.util.ArrayList;
import java.util.List;

/**
 * Equation fragment handles user input of equations for a graph.
 */
public class EquationFragment extends Fragment {

    private List<String> equations;

    public EquationFragment() {
        equations = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equation_layout, container, false);
        equations = new ArrayList<>();
        String graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);

        final RecyclerView recyclerView = view.findViewById(R.id.rv_equation_list);
        Button addEquations = view.findViewById(R.id.b_add_equations);

        final EquationAdapter adapter = new EquationAdapter(equations, graphKey);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // adds dividers between each item in the RecyclerView
        // source: https://stackoverflow.com/questions/31242812/how-can-a-divider-line-be-added-in-an-android-recyclerview
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);
        FirebaseRoutes.getGraphRoute(graphKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> equationsToAdd = dataSnapshot.getValue(EquationList.class).equations;
                    if (equationsToAdd != null) {
                        equations.addAll(dataSnapshot.getValue(EquationList.class).equations);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        addEquations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addEquation();
            }
        });

        return view;
    }
}
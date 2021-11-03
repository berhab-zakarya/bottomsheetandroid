package com.maghosdev.ristoclient.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maghosdev.ristoclient.Model.Plate;
import com.maghosdev.ristoclient.R;
import com.maghosdev.ristoclient.View.CartPageActivity;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class BottomSheetOrderFragment extends BottomSheetDialogFragment {

    public static BottomSheetOrderFragment newInstance(Bundle args) {
        if (args == null) {
            args = new Bundle();
        }
        BottomSheetOrderFragment fragment = new BottomSheetOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet_order, null);
        dialog.setContentView(contentView);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        final Plate plate = (Plate) getArguments().getSerializable("KEY_SER");
        Picasso.get().load(plate.getImage()).transform(transformation).into(((ImageView)contentView.findViewById(R.id.bottom_sheet_img_order)));
        contentView.findViewById(R.id.bottom_sheet_img_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CartPageActivity.class).putExtra("plate_key",plate));
            }
        });
        ((TextView)contentView.findViewById(R.id.bottom_sheet_name_order)).setText(plate.getName());
        ((TextView)contentView.findViewById(R.id.bottom_sheet_price_order)).setText(plate.getPrice());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Resources resources = getResources();

        // Set margin for Landscape Mode. Maybe a more elegant solution will be to implements our own bottom sheet with our own margins.
        if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            assert getView() != null;
            View parent = (View) getView().getParent();
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
            layoutParams.setMargins(
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_margin_left), // 64dp
                    0,
                    resources.getDimensionPixelSize(R.dimen.bottom_sheet_margin_right), // 64dp
                    0
            );
            parent.setLayoutParams(layoutParams);
        }
    }
}

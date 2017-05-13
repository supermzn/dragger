package com.example.dragger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.dragger.GeometryHelper.getDistance;
import static com.example.dragger.GeometryHelper.getPosition;
import static com.example.dragger.GeometryHelper.getRadius;
import static com.example.dragger.SuccessDialog.showSelectedTargetDialog;

public class MainActivity extends AppCompatActivity implements DialogCallback {

    @BindView(R.id.centerButton)
    Button centerButton;
    @BindView(R.id.target1)
    Button target1;
    @BindView(R.id.target2)
    Button target2;
    @BindView(R.id.target3)
    Button target3;
    @BindView(R.id.target4)
    Button target4;

    private final String TAG = getClass().getSimpleName();

    private float mXdifference;
    private float mYdifference;
    private List<Button> mTargetList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTargetList = initTargetList();
        centerButton.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mXdifference = v.getX() - event.getRawX();
                    mYdifference = v.getY() - event.getRawY();
                    v.setPressed(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setX(event.getRawX() + mXdifference);
                    v.setY(event.getRawY() + mYdifference);
                    checkTargetTouching();
                    break;
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
            }
            return true;
        }
    };

    private void checkTargetTouching() {
        for (int i = 0; i < mTargetList.size(); i++) {
            Button tmpTarget = mTargetList.get(i);
            double tmpDistance = getDistance(getPosition(centerButton), getPosition(tmpTarget));
            if (isTouchingTarget(tmpTarget, tmpDistance)) {
                setCenterButtonListener(null);
                onTargetReached(i);
                return;
            }
        }
    }

    private List<Button> initTargetList() {
        List<Button> targetList = new ArrayList<>();
        targetList.add(target1);
        targetList.add(target2);
        targetList.add(target3);
        targetList.add(target4);
        String buttonText = getString(R.string.target);
        for (int i = 0; i < targetList.size(); i++) {
            targetList.get(i).setText(buttonText + " " + (i + 1));
        }
        return targetList;
    }

    private boolean isTouchingTarget(View target, double distance) {
        return distance <= getRadius(centerButton) + getRadius(target);
    }

    private void onTargetReached(int index) {
        showSelectedTargetDialog(this, index + 1, this);
    }

    private void setCenterButtonListener(View.OnTouchListener listener) {
        centerButton.setOnTouchListener(listener);
    }

    @Override
    public void resetCenterButton() {
        centerButton.setPressed(false);
        View parent = (View) centerButton.getParent();
        centerButton.setX((parent.getWidth() / 2 - getRadius(centerButton)));
        centerButton.setY((parent.getHeight() / 2 - getRadius(centerButton)));
        setCenterButtonListener(touchListener);
    }

    @OnClick({R.id.target1, R.id.target2, R.id.target3, R.id.target4})
    public void onTargetClicked() {
        Toast.makeText(this, R.string.drag_center_to_target, Toast.LENGTH_SHORT).show();
    }
}


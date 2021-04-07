package com.suifeng.kotlin.base.lifecycle;

import com.trello.rxlifecycle4.LifecycleTransformer;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

import static com.trello.rxlifecycle4.RxLifecycle.bind;


public class RxLifecycleViewModel {
    public static <T> LifecycleTransformer<T> bindViewModel(final Observable<ViewModelEvent> lifecycle) {
        return bind(lifecycle, VIEW_LIFECYCLE);
    }

    private static final Function<ViewModelEvent, ViewModelEvent> VIEW_LIFECYCLE =
            new Function<ViewModelEvent, ViewModelEvent>() {
                @Override
                public ViewModelEvent apply(ViewModelEvent lastEvent) throws Exception {
                    switch (lastEvent) {
                        case CLEAR:
                            throw new UnsupportedOperationException("Cannot bind to Fragment lifecycle when outside of it.");

                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };
}

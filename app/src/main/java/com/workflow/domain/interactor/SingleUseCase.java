package com.workflow.domain.interactor;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Michael on 14/06/19.
 */

public abstract class SingleUseCase<T, P> {
    private final CompositeDisposable disposables;

    public SingleUseCase() {
        this.disposables = new CompositeDisposable();
    }

    public abstract Single<T> buildUseCaseSingle(P params);

    public void execute(DisposableSingleObserver<T> observer
            , P params) {
        final Single<T> single = buildUseCaseSingle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        addDisposable(single.subscribeWith(observer));
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}

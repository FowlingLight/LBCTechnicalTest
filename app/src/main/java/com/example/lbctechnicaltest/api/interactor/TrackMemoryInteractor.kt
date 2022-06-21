package com.example.lbctechnicaltest.api.interactor

import com.example.lbctechnicaltest.models.Track
import io.reactivex.*
import io.reactivex.subjects.BehaviorSubject

class TrackMemoryInteractor {
    private var observable = BehaviorSubject.create<List<Track>?>()
    private var tracks = mutableListOf<Track>()

    fun saveData(track: List<Track>) {
        this.tracks.clear()
        this.tracks.addAll(track)

        observable.onNext(track)
    }

    fun getTracks(): Maybe<List<Track>> {
        return Maybe.just(tracks)
    }

    val tracksObservable: Observable<List<Track>>
        get() = observable

}
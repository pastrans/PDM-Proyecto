package com.example.grupo9pdm115.Comun;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.example.grupo9pdm115.Modelos.Feriado;

import java.util.Calendar;

public class EventoFeriado extends EventDay implements Parcelable {
    private Feriado feriado;

    public EventoFeriado(Calendar day, int imageResource, Feriado feriado) {
        super(day, imageResource);
        this.feriado = feriado;
    }

    public Feriado getFeriado(){
        return this.feriado;
    }

    private EventoFeriado(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        feriado.setNombreFeriado(in.readString());
    }

    public static final Creator<EventoFeriado> CREATOR = new Creator<EventoFeriado>() {
        @Override
        public EventoFeriado createFromParcel(Parcel in) {
            return new EventoFeriado(in);
        }

        @Override
        public EventoFeriado[] newArray(int size) {
            return new EventoFeriado[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        //parcel.writeInt(this.getImageResource());
        parcel.writeString(feriado.getNombreFeriado());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

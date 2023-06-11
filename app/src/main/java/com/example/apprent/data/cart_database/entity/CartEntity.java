package com.example.apprent.data.cart_database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "cart_products")
public class CartEntity implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;//todo long->int
    private String name;

    public String getFullPath() {
        return fullPath;
    }

    private final String fullPath;
    @TypeConverters(DateConverter.class)
    private Date date;
    private int period;
    private int quantity;
    @TypeConverters(StateConverter.class)
    private State state;
    private final String imageUri;
    private final int minPrice;

    private int finalPrice;

    public enum State {
        CART(1),
        IS_PAID(2),
        ERROR(-1),
        ;

        public final int stateId;

        State(int stateId) {
            this.stateId = stateId;
        }
    }

    protected CartEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        period = in.readInt();
        quantity = in.readInt();
        imageUri = in.readString();
        minPrice = in.readInt();
        finalPrice = in.readInt();
        fullPath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(period);
        dest.writeInt(quantity);
        dest.writeString(imageUri);
        dest.writeInt(minPrice);
        dest.writeInt(finalPrice);
        dest.writeString(fullPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartEntity> CREATOR = new Creator<>() {
        @Override
        public CartEntity createFromParcel(Parcel in) {
            return new CartEntity(in);
        }

        @Override
        public CartEntity[] newArray(int size) {
            return new CartEntity[size];
        }
    };

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public CartEntity(String name, Date date, int period, String imageUri, int minPrice, String fullPath) {
        this.name = name;
        this.date = date;
        this.period = period;
        this.quantity = 1;
        this.imageUri = imageUri;
        this.minPrice = minPrice;
        this.finalPrice = minPrice * period;
        this.state = State.CART;
        this.fullPath = fullPath;
    }

    private void update() {
        this.finalPrice = this.period * this.quantity * this.minPrice;
    }

    public long getId() {
        return id;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPeriod(int period) {
        this.period = period;
        update();
    }

    public int getPeriod() {
        return period;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        update();
    }

    public String getImageUri() {
        return imageUri;
    }

    public static class DateConverter {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }

    public static class StateConverter {
        @TypeConverter
        public static CartEntity.State fromTimestamp(int value) {
            switch (value) {
                case 1:
                    return State.CART;
                case 2:
                    return State.IS_PAID;
                default:
                    return State.ERROR;
            }
        }

        @TypeConverter
        public static int stateToTimestamp(State state) {
            return state.stateId;
        }
    }
}

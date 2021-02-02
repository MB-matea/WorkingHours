package com.mateabeslic.workinghours;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "detail")
public class Detail {

    @PrimaryKey(autoGenerate = true)
    private Integer _id;

//    @ForeignKey(
//            entity = Employee.class,
//            parentColumns = "id_employee",
//            childColumns = "id_fkEmployee"
//    )

    private Integer id_fkEmployee;

    private Date date;

    private Date timeStart;

    private Date timeEnd;


    public Detail(Date date, Date timeStart, Date timeEnd) {
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public Detail(){

    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) { this.date = date; }

    public Date getTimeStart() { return timeStart; }

    public void setTimeStart(Date timeStart) { this.timeStart = timeStart; }

    public Date getTimeEnd() { return timeEnd; }

    public void setTimeEnd(Date timeEnd) { this.timeEnd = timeEnd; }

    public Integer getId_fkEmployee() {
        return id_fkEmployee;
    }

    public void setId_fkEmployee(Integer id_fkEmployee) {
        this.id_fkEmployee = id_fkEmployee;
    }
}

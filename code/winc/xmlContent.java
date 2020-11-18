/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlAttribute
 *  javax.xml.bind.annotation.XmlValue
 */
package wordeotest;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class xmlContent {
    private float start;
    private float dur;
    private String text;

    public xmlContent() {
    }

    public xmlContent(float start, float dur, String text) {
        this.start = start;
        this.dur = dur;
    }

    @XmlAttribute
    public float getStart() {
        return this.start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    @XmlAttribute
    public float getDur() {
        return this.dur;
    }

    public void setDur(float dur) {
        this.dur = dur;
    }

    @XmlValue
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}


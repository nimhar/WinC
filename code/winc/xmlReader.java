/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlElement
 *  javax.xml.bind.annotation.XmlElements
 *  javax.xml.bind.annotation.XmlRootElement
 */
package wordeotest;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import wordeotest.xmlContent;

@XmlRootElement(name="transcript")
public class xmlReader {
    private List<xmlContent> all;

    public xmlReader() {
    }

    public xmlReader(List<xmlContent> all) {
        this.all = all;
    }

    @XmlElements(value={@XmlElement(name="text")})
    public List<xmlContent> getAll() {
        return this.all;
    }

    public void setAll(List<xmlContent> all) {
        this.all = all;
    }
}


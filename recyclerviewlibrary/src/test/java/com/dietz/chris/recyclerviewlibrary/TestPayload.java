package com.dietz.chris.recyclerviewlibrary;

/**
 *
 */
public class TestPayload {

    public final String payload;

    public TestPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public int hashCode() {
        return payload.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TestPayload)) {
            return false;
        }

        return payload.equals(((TestPayload) obj).payload);
    }
}

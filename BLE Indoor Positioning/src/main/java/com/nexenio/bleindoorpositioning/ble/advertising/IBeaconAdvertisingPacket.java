package com.nexenio.bleindoorpositioning.ble.advertising;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.UUID;

/**
 * For advertising packets as specified in Apples <a href="http://www.blueupbeacons.com/docs/dev/Proximity%20Beacon%20Specification%20R1.pdf">Proximity
 * Beacon Specification</a>.
 */

public class IBeaconAdvertisingPacket extends AdvertisingPacket {

    private static final byte[] EXPECTED_FLAGS = {0x02, 0x01, 0x06};
    private static final byte EXPECTED_LENGTH = 0x1A;
    private static final byte EXPECTED_TYPE = (byte) 0xFF;
    private static final byte[] EXPECTED_COMPANY_ID = {0x4C, 0x00};
    private static final byte[] EXPECTED_BEACON_TYPE = {0x02, 0x15};

    private byte[] flagsBytes;
    private byte lengthByte;
    private byte typeByte;
    private byte[] companyIdBytes;
    private byte[] beaconTypeBytes;
    private byte[] proximityUuidBytes;
    private byte[] majorBytes;
    private byte[] minorBytes;
    private byte measuredPowerByte;

    private UUID proximityUuid;
    private int major;
    private int minor;

    public IBeaconAdvertisingPacket(byte[] data) {
        super(data);
    }

    private void parseData() {
        flagsBytes = getFlagsBytes(data);
        lengthByte = getLengthBytes(data);
        typeByte = getTypeBytes(data);
        companyIdBytes = getCompanyIdBytes(data);
        beaconTypeBytes = getBeaconTypeBytes(data);
        proximityUuidBytes = getProximityUuidBytes(data);
        majorBytes = getMajorBytes(data);
        minorBytes = getMinorBytes(data);
        measuredPowerByte = getMeasuredPowerBytes(data);
    }

    @Override
    public String toString() {
        return new StringBuilder("iBeacon Advertising Packet (")
                .append("Proximity UUID: ").append(getProximityUuid(getProximityUuidBytes())).append(" ")
                .append("Major: ").append(getMajor(getMajorBytes())).append(" ")
                .append("Minor: ").append(getMinor(getMinorBytes())).append(" ")
                .append("RSSI at 1m: ").append(getMeasuredPowerByte())
                .append(")")
                .toString();
    }

    public static boolean meetsSpecification(byte[] data) {
        if (data == null || data.length < 29) {
            return false;
        }
        if (getTypeBytes(data) != EXPECTED_TYPE) {
            return false;
        }
        if (!Arrays.equals(getFlagsBytes(data), EXPECTED_FLAGS)) {
            return false;
        }
        if (!Arrays.equals(getBeaconTypeBytes(data), EXPECTED_BEACON_TYPE)) {
            return false;
        }
        return true;
    }

    public static byte[] getFlagsBytes(byte[] data) {
        return Arrays.copyOfRange(data, 0, 3);
    }

    public static byte getLengthBytes(byte[] data) {
        return data[3];
    }

    public static byte getTypeBytes(byte[] data) {
        return data[4];
    }

    public static byte[] getCompanyIdBytes(byte[] data) {
        return Arrays.copyOfRange(data, 5, 5 + 2);
    }

    public static byte[] getBeaconTypeBytes(byte[] data) {
        return Arrays.copyOfRange(data, 7, 7 + 2);
    }

    public static byte[] getProximityUuidBytes(byte[] data) {
        return Arrays.copyOfRange(data, 9, 9 + 24);
    }

    public static byte[] getMajorBytes(byte[] data) {
        return Arrays.copyOfRange(data, 25, 25 + 2);
    }

    public static byte[] getMinorBytes(byte[] data) {
        return Arrays.copyOfRange(data, 27, 27 + 2);
    }

    public static byte getMeasuredPowerBytes(byte[] data) {
        return data[29];
    }

    public static UUID getProximityUuid(byte[] proximityUuidBytes) {
        return AdvertisingPacketUtil.toUuid(proximityUuidBytes);
    }

    public static int getMajor(byte[] majorBytes) {
        return new BigInteger(majorBytes).intValue();
    }

    public static int getMinor(byte[] minorBytes) {
        return new BigInteger(minorBytes).intValue();
    }

    /*
        Getter & Setter
     */

    public byte[] getFlagsBytes() {
        if (flagsBytes == null) {
            flagsBytes = getFlagsBytes(data);
        }
        return flagsBytes;
    }

    public void setFlagsBytes(byte[] flagsBytes) {
        this.flagsBytes = flagsBytes;
    }

    public byte getLengthByte() {
        if (lengthByte == 0) {
            lengthByte = getLengthBytes(data);
        }
        return lengthByte;
    }

    public void setLengthByte(byte lengthByte) {
        this.lengthByte = lengthByte;
    }

    public byte getTypeByte() {
        if (typeByte == 0) {
            typeByte = getTypeBytes(data);
        }
        return typeByte;
    }

    public void setTypeByte(byte typeByte) {
        this.typeByte = typeByte;
    }

    public byte[] getCompanyIdBytes() {
        if (companyIdBytes == null) {
            companyIdBytes = getCompanyIdBytes(data);
        }
        return companyIdBytes;
    }

    public void setCompanyIdBytes(byte[] companyIdBytes) {
        this.companyIdBytes = companyIdBytes;
    }

    public byte[] getBeaconTypeBytes() {
        if (beaconTypeBytes == null) {
            beaconTypeBytes = getBeaconTypeBytes(data);
        }
        return beaconTypeBytes;
    }

    public void setBeaconTypeBytes(byte[] beaconTypeBytes) {
        this.beaconTypeBytes = beaconTypeBytes;
    }

    public byte[] getProximityUuidBytes() {
        if (proximityUuidBytes == null) {
            proximityUuidBytes = getProximityUuidBytes(data);
        }
        return proximityUuidBytes;
    }

    public void setProximityUuidBytes(byte[] proximityUuidBytes) {
        this.proximityUuidBytes = proximityUuidBytes;
    }

    public byte[] getMajorBytes() {
        if (majorBytes == null) {
            majorBytes = getMajorBytes(data);
        }
        return majorBytes;
    }

    public void setMajorBytes(byte[] majorBytes) {
        this.majorBytes = majorBytes;
    }

    public byte[] getMinorBytes() {
        if (minorBytes == null) {
            minorBytes = getMinorBytes(data);
        }
        return minorBytes;
    }

    public void setMinorBytes(byte[] minorBytes) {
        this.minorBytes = minorBytes;
    }

    public byte getMeasuredPowerByte() {
        if (measuredPowerByte == 0) {
            measuredPowerByte = getMeasuredPowerBytes(data);
        }
        return measuredPowerByte;
    }

    public void setMeasuredPowerByte(byte measuredPowerByte) {
        this.measuredPowerByte = measuredPowerByte;
    }

    public UUID getProximityUuid() {
        if (proximityUuid == null) {
            proximityUuid = getProximityUuid(getProximityUuidBytes());
        }
        return proximityUuid;
    }

    public void setProximityUuid(UUID proximityUuid) {
        this.proximityUuid = proximityUuid;
    }

    public int getMajor() {
        if (major == 0) {
            major = getMajor(getMajorBytes());
        }
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        if (minor == 0) {
            minor = getMajor(getMinorBytes());
        }
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

}
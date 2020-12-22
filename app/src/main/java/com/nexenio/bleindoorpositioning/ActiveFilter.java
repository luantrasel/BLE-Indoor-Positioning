package com.nexenio.bleindoorpositioning;
import com.nexenio.bleindoorpositioning.ble.beacon.filter.BeaconFilter;
import com.nexenio.bleindoorpositioning.ble.beacon.filter.IBeaconFilter;
import com.nexenio.bleindoorpositioningdemo.database.FilterModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveFilter {
    private static ActiveFilter activeFilter;

    public List<BeaconFilter> beaconFilters = new ArrayList<>();

    private ActiveFilter() {
    }

    public static ActiveFilter getInstance() {
        if (activeFilter == null) {
            activeFilter = new ActiveFilter();
        }
        return activeFilter;
    }

    public void updateFilters(List<FilterModel> filterModelList) {
        beaconFilters.clear();
        for (FilterModel filter : filterModelList) {
            boolean addFilter = false;
            IBeaconFilter beaconFilter = new IBeaconFilter();
            if (filter.uuid != null && !filter.uuid.isEmpty()) {
                try {
                    beaconFilter.setProximityUuids(
                            UUID.fromString(filter.uuid),
                            UUID.fromString(filter.uuid.toLowerCase()),
                            UUID.fromString(filter.uuid.toUpperCase())
                    );
                    addFilter = true;
                } catch (Exception e) {
                    System.out.println("Invalid UUID" + filter.uuid);
                }
            }
            if (filter.minor != null && !filter.minor.isEmpty()) {
                try {
                    beaconFilter.setMinor(Integer.parseInt(filter.minor));
                    addFilter = true;
                } catch (Exception e) {
                    System.out.println("Invalid minor" + filter.minor);
                }
            }
            if (filter.major != null && !filter.major.isEmpty()) {
                try {
                    beaconFilter.setMajor(Integer.parseInt(filter.major));
                    addFilter = true;
                } catch (Exception e) {
                    System.out.println("Invalid major" + filter.major);
                }
            }

            if (addFilter) {
                System.out.println("ADDDED FILTER");
                beaconFilters.add(beaconFilter);
            }
        }

        System.out.println("new beaconfilters length: "+beaconFilters.size());
    }

}

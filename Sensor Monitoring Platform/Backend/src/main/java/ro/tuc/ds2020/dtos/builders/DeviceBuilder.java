package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.DeviceEntity;

public class DeviceBuilder {
    public DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(DeviceEntity device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(), device.getMaximumEnergyConsumption(), device.getAverageEnergyConsumption(),device.getUser(),device.getSensor());
    }

    public static DeviceEntity toEntity(DeviceDTO deviceDTO) {
        return new DeviceEntity(deviceDTO.getId(),
                deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaximumEnergyConsumption(),
                deviceDTO.getAverageEnergyConsumption(),
                deviceDTO.getUser(),
                deviceDTO.getSensor());
    }
}

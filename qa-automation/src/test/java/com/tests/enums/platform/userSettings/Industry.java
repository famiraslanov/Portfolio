package com.tests.enums.platform.userSettings;

public enum Industry
{
    none("Add your industry"),
    allocator("Allocator"),
    assetManager("Asset Manager"),
    serviceProvider("Service Provider"),
    ResearchAcademia("Research/Academia"),
    PublicNFPBodies("Public/NFP Bodies");

    public final String name;

    Industry(String name)
    {
        this.name = name;
    }

    public static Industry getEnumByName(String name) {
        for (Industry industry : Industry.values()) {
            if (industry.name.equals(name)) {
                return industry;
            }
        }
        return Industry.none;
    }
}

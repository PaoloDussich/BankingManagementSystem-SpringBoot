package com.example.bankingmanagementsystemspringboot.Users;

import java.util.Set;


    public enum Role {
        ADMIN(Set.of(
                Permissions.READ_CLIENTS,
                Permissions.CREATE_CLIENT,
                Permissions.UPDATE_CLIENT,
                Permissions.DELETE_CLIENT,
                Permissions.DEPOSIT,
                Permissions.WITHDRAW,
                Permissions.TRANSFER,
                Permissions.VIEW_TRANSACTIONS,
                Permissions.VIEW_TRANSFERS
        )),

        CLIENT(Set.of(Permissions.DEPOSIT, Permissions.WITHDRAW, Permissions.TRANSFER, Permissions.VIEW_TRANSACTIONS, Permissions.VIEW_TRANSFERS));

        private final Set<Permissions> defaultPermissions;

        Role(Set<Permissions> defaultPermissions) {
            this.defaultPermissions = defaultPermissions;
        }

        public Set<Permissions> getDefaultPermissions() {
            return defaultPermissions;
        }

    }

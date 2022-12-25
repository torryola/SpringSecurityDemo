package net.torrydev.stockmanagementsystem.authentication;

import net.torrydev.stockmanagementsystem.model.AppUserDetails;

import java.util.Optional;

/**
 * This can extend JpaRepository
 * e.g. AppUserDetailsDao extends JpaRepository<AppUserDetails, Long>
 */
public interface AppUserDetailsDao{
    Optional<AppUserDetails> findAppUserByUserName(String userName);
}

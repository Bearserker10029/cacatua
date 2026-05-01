package org.example.cacatua.repository;

import org.example.cacatua.model.TableroInicialBackup;
import org.example.cacatua.model.TableroInicialBackupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tableroInicialBackupRepository extends JpaRepository<TableroInicialBackup, TableroInicialBackupId> {
}
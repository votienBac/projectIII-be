package vn.noron.repository.dialog;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import vn.noron.data.Tables;
import vn.noron.data.tables.pojos.Dialog;
import vn.noron.data.tables.records.DialogRecord;
import vn.noron.repository.AbsRepository;

import static vn.noron.data.Tables.DIALOG;
@Repository
public class DialogRepositoryImpl extends AbsRepository<DialogRecord, Dialog, Long> implements IDialogRepository {
    @Override
    protected TableImpl<DialogRecord> getTable() {
        return DIALOG;
    }
}

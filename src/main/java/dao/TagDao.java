package dao;

import generated.tables.records.TagsRecord;
import generated.tables.records.ReceiptsRecord;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;
public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public void checkIfExists(Integer id, String tag){
        TagsRecord checkIfExistsBool = dsl.selectFrom(TAGS)
                .where(TAGS.TAG_VALUE.eq(tag))
                .and(TAGS.RECEIPT_ID.eq(id))
                .fetchOne();

        if (checkIfExistsBool == null) {
            dsl.insertInto(TAGS, TAGS.RECEIPT_ID, TAGS.TAG_VALUE)
                    .values(id, tag)
                    .execute();
        }
        else {
            dsl.delete(TAGS)
                    .where(TAGS.RECEIPT_ID.eq(id))
                    .and((TAGS.TAG_VALUE.eq(tag)))
                    .execute();
        }

    }

    public List<ReceiptsRecord> getReceiptsByTag(String tag) {
        List<ReceiptsRecord> taggedReceipts = dsl.select()
                .from(RECEIPTS)
                .join(TAGS).on(TAGS.RECEIPT_ID.eq(RECEIPTS.ID))
                .where(TAGS.TAG_VALUE.eq(tag))
                .fetchInto(RECEIPTS);

        return taggedReceipts;
    }

    public List<TagsRecord> getTagsForEachReceipt(int id) {
        return dsl.selectFrom(TAGS).where(TAGS.RECEIPT_ID.eq(id)).fetch();
    }


}





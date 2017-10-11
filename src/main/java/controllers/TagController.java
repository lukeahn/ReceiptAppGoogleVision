package controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import dao.TagDao;

import api.ReceiptResponse;
import api.TagResponse;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagController  {
    final TagDao tag;

    public TagController(TagDao tag) {
        this.tag = tag;
    }

    @PUT
    @Path("/{tag}")
    public void toggleTag(@PathParam("tag") String tagName, int id) {
        tag.checkIfExists(id,tagName);
//        System.out.print("Put tag has been called ");
//        System.out.print(tagName +'\n');
//        System.out.print("ID: ");
//        System.out.print(id);
//        System.out.print('\n');
    }

    @GET
    @Path("/{tag}")
    public List<ReceiptResponse> getReceiptsByTag(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptRecords = tag.getReceiptsByTag(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }

    @GET
    @Path("/receipt/{ID}")
    public List<TagResponse> getTagsForEachReceipt(@PathParam("ID") int ID) {
        List<TagsRecord> tagsRecords = tag.getTagsForEachReceipt(ID);
        return tagsRecords.stream().map(TagResponse::new).collect(toList());
    }
}



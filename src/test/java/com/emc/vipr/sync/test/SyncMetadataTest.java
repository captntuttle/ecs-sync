package com.emc.vipr.sync.test;

import com.emc.atmos.api.bean.Permission;
import com.emc.vipr.sync.model.Checksum;
import com.emc.vipr.sync.model.SyncAcl;
import com.emc.vipr.sync.model.SyncMetadata;
import com.emc.vipr.sync.util.Iso8601Util;
import com.emc.vipr.sync.util.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SyncMetadataTest {
    @Test
    public void testMultiValueMap() {
        MultiValueMap<String, String> one = new MultiValueMap<>();
        MultiValueMap<String, String> two = new MultiValueMap<>();

        // test same keys, different values
        one.add("foo", "bar");
        two.add("foo", null);
        one.add("bar", "baz");
        two.add("bar", "foo");
        Assert.assertNotEquals(one, two);

        // test after update
        two.putSingle("bar", "baz");
        two.putSingle("foo", "bar");
        Assert.assertEquals(one, two);

        // test extra key
        two.add("oh", "yeah");
        Assert.assertNotEquals(one, two);

        // test after delete
        two.remove("oh");
        Assert.assertEquals(one, two);

        // test different sized value lists
        two.add("foo", "yo!");
        Assert.assertNotEquals(one, two);
    }

    @Test
    public void testJsonSerialization() {
        Map<String, Object> userMeta = new HashMap<>();
        MultiValueMap<String, String> userGrants = new MultiValueMap<>();
        MultiValueMap<String, String> groupGrants = new MultiValueMap<>();

        groupGrants.add("other", Permission.READ.toString());
        userGrants.add("stu", Permission.FULL_CONTROL.toString());
        userGrants.add("jason", Permission.NONE.toString());

        userMeta.put("foo", "bar");
        userMeta.put("baz", null);
        userMeta.put("crazy", " b|1+NB8o6%g HE@dIfAxm  ");

        SyncAcl acl = new SyncAcl();
        acl.setOwner("stu");
        acl.setUserGrants(userGrants);
        acl.setGroupGrants(groupGrants);

        SyncMetadata metadata = new SyncMetadata();
        metadata.setSize(210881);
        metadata.setUserMetadata(userMeta);
        metadata.setAcl(acl);
        metadata.setChecksum(new Checksum("MD5", "xxyyxxyy"));
        metadata.setContentType("application/ms-excel");
        metadata.setModificationTime(new Date());
        metadata.setExpirationDate(new Date());

        String jsonString = metadata.toJson();

        SyncMetadata mFromJson = SyncMetadata.fromJson(jsonString);
        Assert.assertEquals(metadata.getSize(), mFromJson.getSize());
        Assert.assertEquals(metadata.getAcl().getOwner(), mFromJson.getAcl().getOwner());
        Assert.assertEquals(metadata.getAcl().getUserGrants(), mFromJson.getAcl().getUserGrants());
        Assert.assertEquals(metadata.getAcl().getGroupGrants(), mFromJson.getAcl().getGroupGrants());
        Assert.assertEquals(metadata.getUserMetadata(), mFromJson.getUserMetadata());
        Assert.assertEquals(metadata.getContentType(), mFromJson.getContentType());
        Assert.assertEquals(metadata.getChecksum(), mFromJson.getChecksum());
        Assert.assertEquals(Iso8601Util.format(metadata.getModificationTime()),
                Iso8601Util.format(mFromJson.getModificationTime()));
        Assert.assertEquals(Iso8601Util.format(metadata.getExpirationDate()),
                Iso8601Util.format(mFromJson.getExpirationDate()));
    }

    @Test
    public void testPathTranslation() {
        String sourcePath = "/foo/bar/baz.zip/dir";
        String metaPath = SyncMetadata.getMetaPath(sourcePath, true);
        Assert.assertEquals("dir failed", "/foo/bar/baz.zip/dir/" + SyncMetadata.METADATA_DIR + "/" + SyncMetadata.DIR_META_FILE + "", metaPath);

        sourcePath = "/foo/bar/baz.zip/dir/object";
        metaPath = SyncMetadata.getMetaPath(sourcePath, false);
        Assert.assertEquals("object failed", "/foo/bar/baz.zip/dir/" + SyncMetadata.METADATA_DIR + "/object", metaPath);

        sourcePath = "foo/bar/baz/object";
        metaPath = SyncMetadata.getMetaPath(sourcePath, false);
        Assert.assertEquals("relative path failed", "foo/bar/baz/" + SyncMetadata.METADATA_DIR + "/object", metaPath);
    }

    @Test
    public void testSyncAclClone() throws Exception {
        SyncAcl one = new SyncAcl();
        one.setOwner("bob");
        one.getUserGrants().add("bob", "all");
        one.getGroupGrants().add("people", "read");

        SyncAcl two = (SyncAcl) one.clone();
        two.setOwner("john");
        two.getUserGrants().add("bob", "acl");
        two.getGroupGrants().add("everyone", "read");

        Assert.assertFalse(one == two);
        Assert.assertFalse(one.getUserGrants() == two.getUserGrants());
        Assert.assertFalse(one.getGroupGrants() == two.getGroupGrants());
        Assert.assertFalse(one.getUserGrants().get("bob") == two.getUserGrants().get("bob"));
        Assert.assertEquals("bob", one.getOwner());
        Assert.assertEquals(1, one.getUserGrants().get("bob").size());
        Assert.assertEquals(2, two.getUserGrants().get("bob").size());
        Assert.assertEquals(1, one.getGroupGrants().size());
        Assert.assertEquals(2, two.getGroupGrants().size());
    }
}
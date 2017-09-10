package com.revauc.revolutionbuy.database;


import com.revauc.revolutionbuy.network.response.contest.ContestLive;

import io.realm.Realm;
import io.realm.RealmResults;

public class LiveRealmController {
    private final String TAG = "RealmController";

    public Realm realm;

    //This constructor must be called only for UI Thread
    //Don't call this method from worker thread, it will throw exception otherwise
    public LiveRealmController() {
        realm = RealmManager.getRealm();
    }

    //This constructor must be called for each worker Thread
    public LiveRealmController(Realm realm) {
        this.realm = realm;
    }

    public void saveLiveContest(ContestLive contest) {
        if (contest == null) {
            return;
        }
        if (!realm.isInTransaction())
            realm.beginTransaction();
        try {

            realm.copyToRealmOrUpdate(contest);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            realm.commitTransaction();
        }
    }

    public RealmResults<ContestLive> getAllLiveContests(String contestType) {

        RealmResults<ContestLive> contactDataList;
        contactDataList = realm.where(ContestLive.class).equalTo("type", contestType).findAll();
        return contactDataList;
    }

    public boolean removeAllLiveContests(String contestType) {
        realm.beginTransaction();
        boolean isDeleted = realm.where(ContestLive.class).equalTo("type", contestType).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        return isDeleted;

    }


    //    public RealmList<ChatMessage> getAllUnsentMessages(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmList<ChatMessage> unsentChat = new RealmList<>();
//        try {
//            RealmResults<ChatThreads> unsentThreads = realm.where(ChatThreads.class).equalTo("messages.msgStatus", AppConstants.MsgStatus.unsent.getValue())
//                    .findAll();
//            for (ChatThreads chat : unsentThreads) {
//
//                RealmResults<ChatMessage> unsentMessages = chat.getMessages().where().equalTo("msgStatus", AppConstants.MsgStatus.unsent.getValue()).findAll();
//                unsentChat.addAll(unsentMessages);
//
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return unsentChat;
//    }
//
//    public RealmResults<ChatMessage> getAllUnsentImageMessages(Realm realm, String friendJId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmResults<ChatMessage> unsentMessages = null;
//        try {
//            realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst()
//                    .getMessages().where().equalTo("msgStatus", AppConstants.MsgStatus.unsent.getValue())
//                    .equalTo("mediaType",AppConstants.MsgType.image.toString()).findAll()
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return unsentMessages;
//    }
//
//
//    public void selectThread(Realm realm, ChatThreads chatThreads) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            chatThreads.setSelected(true);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void unselectThread(Realm realm, ChatThreads chatThreads) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            chatThreads.setSelected(false);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void unselectAllSelectedThreads(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            RealmResults<ChatThreads> selectedContacts = realm.where(ChatThreads.class).equalTo("isSelected", true).findAll();
//            for (ChatThreads chatThreads : selectedContacts) {
//                chatThreads.setSelected(false);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void deleteAllSelectedThreads(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            realm.where(ChatThreads.class).equalTo("isSelected", true).findAll().deleteAllFromRealm();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void readAllSelectedThreads(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            RealmResults<ChatThreads> selectedContacts = realm.where(ChatThreads.class).equalTo("isSelected", true).findAll();
//            for (ChatThreads chatThread : selectedContacts) {
//                chatThread.setUnread(false);
//                RealmResults<ChatMessage> chatMessagesToUpdate = chatThread.getMessages().where().equalTo("msgStatus", AppConstants.MsgStatus.unread.getValue()).findAll();
//                if (chatMessagesToUpdate != null) {
//                    for (ChatMessage chatMessage : chatMessagesToUpdate) {
//                        chatMessage.setMsgStatus(AppConstants.MsgStatus.read.getValue());
//                    }
//                }
//                chatThread.setUnreadCount(0);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//
//    public void unreadAllSelectedThreads(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            RealmResults<ChatThreads> selectedContacts = realm.where(ChatThreads.class).equalTo("isSelected", true).findAll();
//            for (ChatThreads chatThreads : selectedContacts) {
//                chatThreads.setUnread(true);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//
//    public boolean deleteChatThread(Realm realm, String groupJid) {
//
//        boolean isSuccess = true;
//        if (StringUtils.isNullOrEmpty(groupJid)) {
//            return false;
//        }
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            if (checkIfChatThreadExists(realm, groupJid)) {
//                realm.where(ChatThreads.class).equalTo("jabberId", groupJid).findFirst().deleteFromRealm();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            isSuccess = false;
//        } finally {
//            realm.commitTransaction();
//        }
//        return isSuccess;
//
//    }
//
//    public RealmResults<ChatThreads> getAllSelectedThreads(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmResults<ChatThreads> threadList = null;
//        try {
//            threadList = realm.where(ChatThreads.class).equalTo("isSelected", true).findAll();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return threadList;
//    }
//
//    public long getSelectedThreadCount(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        long selectedCount = 0;
//        try {
//
//            selectedCount = realm.where(ChatThreads.class).equalTo("isSelected", true).count();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return selectedCount;
//    }
//
//
//    public long getSelectedAndReadThreadCount(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        long selectedCount = 0;
//        try {
//
//            selectedCount = realm.where(ChatThreads.class).equalTo("isSelected", true).equalTo("unreadCount", 0).equalTo("isUnread", false).count();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return selectedCount;
//    }
//
//    public long getSelectedAndUnReadThreadCount(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        long selectedCount = 0;
//        try {
//            long selectedUnreadCount = realm.where(ChatThreads.class).equalTo("isSelected", true).greaterThan("unreadCount", 0).count();
//            long selectedIsUnread = realm.where(ChatThreads.class).equalTo("isSelected", true).equalTo("isUnread", true).count();
//
//            selectedCount = selectedUnreadCount + selectedIsUnread;
////            selectedCount = realm.where(ChatThreads.class).equalTo("isSelected", true).greaterThan("unreadCount", 0).or().equalTo("isUnread", true).count();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return selectedCount;
//    }
//
//
//    //---------- Message Deletion
//    public void selectMessage(Realm realm, ChatMessage chatMessage) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            chatMessage.setSelected(true);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void unselectMessage(Realm realm, ChatMessage chatMessage) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            chatMessage.setSelected(false);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public void unselectAllSelectedMessages(Realm realm, String friendJId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                RealmResults<ChatMessage> messages = chatThreads.getMessages().where().equalTo("isSelected", true).findAll();
//                for (ChatMessage chatMessage : messages) {
//                    chatMessage.setSelected(false);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//    public boolean deleteAllSelectedMessages(Realm realm, String friendJId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        boolean messageListEmpty = false;
//        try {
//            if (!realm.isInTransaction())
//                realm.beginTransaction();
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                chatThreads.getMessages().where().equalTo("isSelected", true).findAll().deleteAllFromRealm();
//                RealmList<ChatMessage> lst = chatThreads.getMessages();
//                if (lst == null || lst.size() <= 1 && !Utils.isGroupJid(friendJId)) {
//                    chatThreads.deleteFromRealm();
//                    messageListEmpty = true;
//                } else {
//                    ChatMessage lastMessage = chatThreads.getMessages().where().equalTo("userType", AppConstants.UserType.sender.toString()).or().equalTo("userType", AppConstants.UserType.receiver.toString()).or().equalTo("userType", AppConstants.UserType.headerMsg.toString()).findAll().last();
//                    if (lastMessage != null) {
//                        chatThreads.setLatestMessage(lastMessage);
//                    } else {
//                        chatThreads.setLatestMessage(null);
//                    }
//                }
//
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//            return messageListEmpty;
//        }
//
//    }
//
//    public long getSelectedMessageCount(Realm realm, String friendJId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        long selectedCount = 0;
//        try {
//
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                selectedCount = chatThreads.getMessages().where().equalTo("isSelected", true).count();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return selectedCount;
//    }
//
//    public RealmResults<ChatMessage> getAllSelectedMessages(Realm realm, String friendJId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmResults<ChatMessage> messageList = null;
//        try {
//
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                messageList = chatThreads.getMessages().where().equalTo("isSelected", true).findAll();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return messageList;
//    }
//
//
//    public void addTypingMessage(Realm realm, String friendJId, boolean startTransaction) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (startTransaction && !realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                if (chatThreads.getMessages() != null && chatThreads.getMessages().where().equalTo("isEmptyMsg", true).count() == 0) {
//                    chatThreads.getMessages().add(Utils.getEmptySenderMessage(realm, friendJId));
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (startTransaction && realm.isInTransaction())
//                realm.commitTransaction();
//        }
//
//    }
//
//    public void removeTypingMessage(Realm realm, String friendJId, boolean startTransaction) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (startTransaction && !realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//            if (chatThreads != null) {
//                RealmResults<ChatMessage> messages = chatThreads.getMessages().where().equalTo("isEmptyMsg", true).findAll();
//                chatThreads.getMessages().removeAll(messages);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (startTransaction && realm.isInTransaction())
//                realm.commitTransaction();
//        }
//
//    }
//
//    public boolean saveChatMessage(Realm realm, ChatMessage chatMessage) {
//        boolean savedSuccessfully = true;
//        if (chatMessage == null) return false;
//        String friendJId = Utils.getFriendJabberIdFromMsg(chatMessage);
//        if (StringUtils.isNullOrEmpty(friendJId)) {
//            LogUtils.LOGE(TAG, "Cannot save chat message with null jabber id");
//            return false;
//        }
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//
//        try {
//            ChatMessage chatMessageNew = realm.copyToRealm(chatMessage);
//            if (chatMessageNew.isDeleted()) return false;
//
//            friendJId = friendJId.toLowerCase();
//            if (checkIfChatThreadExists(realm, friendJId)) {
//                //Add value to the thread
//                ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", friendJId).findFirst();
//                RealmList<ChatMessage> msgList = chatThreads.getMessages();
//                if (msgList.size() > 0 && msgList.last().isEmptyMsg()) {
//                    //REMOVE TYPING
////                    if (chatMessageNew.getUserType().equalsIgnoreCase(AppConstants.UserType.receiver.toString()))
//                    removeTypingMessage(realm, friendJId, false);
//
//                    //CHECK DATE WITH LAST
//                    String lastdate = msgList.last().getMsgDate();
//                    if (lastdate != null && chatMessageNew.getMsgDate().equalsIgnoreCase(lastdate)) {
//                        //Do Nothing
//                    } else {
//                        //ADD DATE HEADER
////                        if (StringUtils.isNullOrEmpty(chatMessageNew.getMsgDate())) {
////                            chatThreads.getMessages().add(Utils.getDateHeaderMessage(realm, null));
////                        } else {
//                        long dateHeaders = chatThreads.getMessages().where().equalTo("userType",AppConstants.UserType.dateHeader.toString()).equalTo("msgDate",chatMessageNew.getMsgDate()).count();
//                        if(dateHeaders==0)
//                        {
//                            chatThreads.getMessages().add(Utils.getDateHeaderMessage(realm, chatMessageNew.getMsgDate(), chatMessageNew.getMsgTime(), chatMessageNew.getMsgTimestamp()));
//                        }
////                        }
//                    }
//                    //ADD NEW MESSAGE
//                    chatThreads.getMessages().add(chatMessageNew);
//                    chatThreads.setLatestMessage(chatMessageNew);
//                    chatThreads.setLatestTimeStamp(chatMessageNew.getMsgTimestamp());
//
//                    //ADD TYPING AGAIN
//                    addTypingMessage(realm, friendJId, false);
//                } else {
//
//                    String lastdate = null;
//                    //CHECK DATE WITH LAST
//                    if (msgList.size() > 0 && !StringUtils.isNullOrEmpty(msgList.last().getMsgDate())) {
//                        lastdate = msgList.last().getMsgDate();
//                    }
//
//                    if (lastdate != null && chatMessageNew.getMsgDate().equalsIgnoreCase(lastdate)) {
//                        //Do Nothing
//                    } else {
//                        //ADD DATE HEADER
////                        if (StringUtils.isNullOrEmpty(chatMessageNew.getMsgDate())) {
////                            chatThreads.getMessages().add(Utils.getDateHeaderMessage(realm, null));
////                        } else {
//                        long dateHeaders = chatThreads.getMessages().where().equalTo("userType",AppConstants.UserType.dateHeader.toString()).equalTo("msgDate",chatMessageNew.getMsgDate()).count();
//                        if(dateHeaders==0)
//                        {
//                            chatThreads.getMessages().add(Utils.getDateHeaderMessage(realm, chatMessageNew.getMsgDate(), chatMessageNew.getMsgTime(), chatMessageNew.getMsgTimestamp()));
//                        }
////                        }
//                    }
//                    //ADD NEW MESSAGE
//                    chatThreads.getMessages().add(chatMessageNew);
//                    chatThreads.setLatestMessage(chatMessageNew);
//                    chatThreads.setLatestTimeStamp(chatMessageNew.getMsgTimestamp());
//                }
//            } else {
//                //Create a thread
//                ChatThreads chatThreads = realm.createObject(ChatThreads.class, friendJId);
//                //Add date header
//                chatThreads.getMessages().add(Utils.getDateHeaderMessage(realm, chatMessageNew.getMsgDate(), chatMessageNew.getMsgTime(), chatMessageNew.getMsgTimestamp()));
//                //Add First Message
//                chatThreads.getMessages().add(chatMessageNew);
//                chatThreads.setLatestMessage(chatMessageNew);
//                chatThreads.setLatestTimeStamp(chatMessageNew.getMsgTimestamp());
//                chatThreads.setNotificationId(PreferenceUtil.createNotificationId());
//            }
//        } catch (RealmPrimaryKeyConstraintException e) {
//            e.printStackTrace();
//            LogUtils.LOGE("SaveMessage", friendJId + "\n" + chatMessage.getMsgText() + " already exists");
//            savedSuccessfully = false;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            savedSuccessfully = false;
//        } finally {
//            realm.commitTransaction();
//        }
//
//        return savedSuccessfully;
//    }
//
//
//    public void saveSessionKeyWithNewChat(Realm realm, String friendJId, String ssk) {
//
//        if (StringUtils.isNullOrEmpty(friendJId)) {
//            LogUtils.LOGE(TAG, "Cannot save chat message with null jabber id");
//            return;
//        }
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//
//        try {
//            ChatThreads chatThreads = realm.createObject(ChatThreads.class, friendJId);
//            chatThreads.setLatestTimeStamp(DateTimeUtils.getCurrentUTCTimeStamp());
//            chatThreads.setSessionKey(ssk);
//            // chatThreads.setLatestMessage();
//            chatThreads.setNotificationId(PreferenceUtil.createNotificationId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//
//    }
//
//    public RealmResults<ChatMessage> getAllMsgByJabberId(Realm realm, String jabberId) {
//
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmResults<ChatMessage> chatMessages =null;
//        try {
//            if (checkIfChatThreadExists(realm, jabberId)) {
//                LogUtils.LOGI(">>>>>", "Yes, Chat Thread Exists");
//                //Add value to the thread
//                ChatThreads thread = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
//                if (thread != null) {
//                    chatMessages = thread.getMessages().sort("msgTimestamp");
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return chatMessages;
//    }
//
//    public ChatThreads getChatThreadByJabberId(Realm realm, String jabberId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        ChatThreads chatThreads = null;
//        try {
//            if (checkIfChatThreadExists(realm, jabberId)) {
//                //Add value to the thread
//                chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return chatThreads;
//    }
//
//    public void setSSkinChatThreadByJabberId(Realm realm, String jabberId, String ssk) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//
//        ChatThreads chatThreads = null;
//        try {
//            if (checkIfChatThreadExists(realm, jabberId)) {
//                //Add value to the thread
//                chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
//                chatThreads.setSessionKey(ssk);
//
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//    }
//
//
//    public ChatMessage getLastReceivedMessage(Realm realm, String jabberId) {
//        if (StringUtils.isNullOrEmpty(jabberId)) {
//            LogUtils.LOGW(TAG, "null jid cannot process");
//            return null;
//        }
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        ChatMessage chatMessage = null;
//        try {
//            if (checkIfChatThreadExists(realm, jabberId)) {
//                ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
//                RealmQuery<ChatMessage> result = chatThreads.getMessages().where().equalTo("userType", AppConstants.UserType.receiver.toString()).or().equalTo("userType", AppConstants.UserType.dateHeader.toString()).or().equalTo("userType", AppConstants.UserType.headerMsg.toString());
//                if (result != null && !realm.isEmpty()) {
//                    chatMessage = result.findAllSorted("msgTimestamp", Sort.DESCENDING).first();
//                }
////                else{
////                    result = chatThreads.getMessages().where().equalTo("userType", AppConstants.UserType.dateHeader.toString()).or().equalTo("userType", AppConstants.UserType.headerMsg.toString());
////                    chatMessage = result.findAllSorted("msgTimestamp", Sort.DESCENDING).first();
////                }
//
////                if (chatMessage == null) {
////                    chatMessage = chatThreads.getMessages().where().equalTo("userType", AppConstants.UserType.sender.toString()).findAllSorted("msgTimestamp", Sort.DESCENDING).first();
////                }
////
////                if (chatMessage == null) {
////                    chatMessage = chatThreads.getMessages().where().equalTo("userType", AppConstants.UserType.headerMsg.toString()).findAllSorted("msgTimestamp", Sort.DESCENDING).first();
////                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return chatMessage;
//    }
//
//    public void updateMessageStatusByMsgIdAndJabberId(Realm realm, String friendJabberId, String msgId, int msgStatus, String fromJid) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//
//
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//                //Add value to the thread
//                ChatMessage chatMessageToUpdate = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("msgId", msgId).findFirst();
//                if (chatMessageToUpdate != null) {
//                    if (Utils.isGroupJid(friendJabberId)) {
//
//                        updateGroupMsg(realm, msgStatus, fromJid, chatMessageToUpdate, friendJabberId);
//                    } else
//                        chatMessageToUpdate.setMsgStatus(msgStatus);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//    }
//
//    public void updateMsgToReadStatusSent(Realm realm, String friendJabberId, String msgId, String fromJid) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//
//
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//                //Add value to the thread
//                ChatMessage chatMessageToUpdate = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("msgId", msgId).findFirst();
//                if (chatMessageToUpdate != null) {
//                    chatMessageToUpdate.setReadStatusSent(true);
////                    if (Utils.isGroupJid(friendJabberId)) {
////
////                        updateGroupMsg(realm, msgStatus, fromJid, chatMessageToUpdate, friendJabberId);
////                    } else
////                        chatMessageToUpdate.setMsgStatus(msgStatus);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//    }
//
//
//    // no need to start transaction in this method because realm object is passed as parameter is already in write transaction
//    private void updateGroupMsg(Realm realm, int msgStatus, String fromJid, ChatMessage chatMessageToUpdate, String friendJabberId) {
//        RealmList<GroupUser> members;
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        members = new RealmControllerGroup(realm).getGroupInfoByGroupId(friendJabberId).getGroupUsers();
//
//        int size = 0;
//        if (members != null && members.size() > 0) {
//            size = members.size();
//        }
//        if (chatMessageToUpdate.getMsgStatus() == AppConstants.MsgStatus.unsent.getValue()) {
//            chatMessageToUpdate.setMsgStatus(AppConstants.MsgStatus.sent.getValue());
//        }
//        if (msgStatus == AppConstants.MsgStatus.delivered.getValue()) {
//            if (fromJid != null)
//                chatMessageToUpdate.getRealmListMsgDelivered().add(new ChatUser(fromJid));
//            if (chatMessageToUpdate.getRealmListMsgDelivered().size() >= size - 1)
//                chatMessageToUpdate.setMsgStatus(msgStatus);
//
//        } else if (msgStatus == AppConstants.MsgStatus.read.getValue()) {
//            if (fromJid != null) {
//                chatMessageToUpdate.getRealmListMsgSeen().add(new ChatUser(fromJid));
//                chatMessageToUpdate.getRealmListMsgDelivered().add(new ChatUser(fromJid));
//            }
//
//            if (chatMessageToUpdate.getRealmListMsgSeen().size() >= size - 1)
//                chatMessageToUpdate.setMsgStatus(msgStatus);
//            else if (chatMessageToUpdate.getRealmListMsgDelivered().size() >= size - 1)
//                chatMessageToUpdate.setMsgStatus(AppConstants.MsgStatus.delivered.getValue());
//        } else {
//            chatMessageToUpdate.setMsgStatus(msgStatus);
//        }
//    }
//
//    public void updateAllSentMessageStatusByJabberId(Realm realm, String friendJabberId, int msgStatus) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//                //Add value to the thread
//                RealmResults<ChatMessage> chatMessageRealmList = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("userType", AppConstants.UserType.sender.toString()).findAll();
//                for (ChatMessage chatMessage : chatMessageRealmList) {
//                    chatMessage.setMsgStatus(msgStatus);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//
//        }
//    }
//
////    public void updateMessageIdByMsgIdAndJabberId(String friendJabberId, String oldmsgId, String newMsgId) {
////        realm.beginTransaction();
////        try {
////            if (checkIfChatThreadExists(friendJabberId)) {
////                Log.d(">>>>>", "Yes, Chat Thread Exists");
////                //Add value to the thread
////                ChatMessage chatMessageToUpdate = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("msgId", oldmsgId).findFirst();
////                if (chatMessageToUpdate != null) {
////                    chatMessageToUpdate.setMsgId(newMsgId);
////                    Log.d(">>>>>", "old message id :" + oldmsgId + " to new message id :" + newMsgId + " updated");
////                }
////            } else {
////                Log.d(">>>>>", "No, Chat Thread dont Exists");
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        } finally {
////            realm.commitTransaction();
////        }
////    }
//
//    public void markMessagesAsRead(Realm realm, String friendJabberId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//                ChatThreads chatThread = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst();
//                //Add value to the thread
//                if (chatThread != null) {
//                    RealmResults<ChatMessage> chatMessagesToUpdate = chatThread.getMessages().where().equalTo("msgStatus", AppConstants.MsgStatus.unread.getValue()).findAll();
//                    if (chatMessagesToUpdate != null) {
//                        for (ChatMessage chatMessage : chatMessagesToUpdate) {
//                            chatMessage.setMsgStatus(AppConstants.MsgStatus.read.getValue());
//                        }
//                    }
//                    chatThread.setUnreadCount(0);
//                    chatThread.setUnread(false);
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//
//        }
//    }
//
//    public void updateMessageByMsgIdAndJabberId(Realm realm, String friendJabberId, String messageId, ChatMessage chatMessage) {
//        if (chatMessage == null) return;
//
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//
//                ChatMessage chatMessageToUpdate = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("msgId", messageId).findFirst();
//                if (chatMessageToUpdate != null) {
//                    copyMessage(realm, chatMessageToUpdate, chatMessage, false);
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//
//    }
//
//    public void deleteMessageByMsgId(Realm realm, String friendJabberId, String messageId) {
//
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//
//                ChatMessage chatMessageToDelete = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst().getMessages().where().equalTo("msgId", messageId).findFirst();
//                if (chatMessageToDelete != null) {
//                    chatMessageToDelete.deleteFromRealm();
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//
//
//    }
//
//    public void updateUnreadStatus(Realm realm, String friendJabberId) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        if (!realm.isInTransaction())
//            realm.beginTransaction();
//        try {
//            if (checkIfChatThreadExists(realm, friendJabberId)) {
//
//                ChatThreads chatThread = realm.where(ChatThreads.class).equalTo("jabberId", friendJabberId).findFirst();
//                if (chatThread != null) {
//                    long unreadCount = chatThread.getMessages().where().equalTo("msgStatus", AppConstants.MsgStatus.unread.getValue()).count();
//                    chatThread.setUnreadCount(unreadCount);
//                    if (unreadCount > 0) {
//                        chatThread.setUnread(false);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            realm.commitTransaction();
//        }
//    }
//
//
//    public RealmResults<ChatThreads> getAllUserMessageList(Realm realm) {
//        if (realm == null) {
//            realm = RealmManager.getRealm();
//        }
//        RealmResults<ChatThreads> chatMessages = null;
//        try {
////            if (checkIfChatsExists()) {
//            //Add value to the thread
//            chatMessages = realm.where(ChatThreads.class).contains("jabberId", AppConstants.GROUP_CHAT_JID_SUFFIX).or().isNotEmpty("messages").findAllSorted("latestTimeStamp", Sort.DESCENDING);
////            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return chatMessages;
//    }
//
//
//    public boolean checkIfChatThreadExists(Realm realm, String id) {
//
//        RealmQuery<ChatThreads> query = realm.where(ChatThreads.class)
//                .equalTo("jabberId", id.toLowerCase());
//
//        return query.count() != 0;
//    }
//
//
//    public boolean checkIfChatsExists(Realm realm) {
//
//        ChatThreads chatThreads = realm.where(ChatThreads.class).findFirst();
//
//        return (chatThreads != null);
//    }
//
//    public void copyMessage(Realm realm, ChatMessage msgTo, ChatMessage msgFrom, boolean startTransaction) {
//        if (msgTo == null || msgFrom == null) return;
//
//        if (startTransaction && !realm.isInTransaction()) realm.beginTransaction();
//        try {
//            if (msgFrom.getFromJid() != null) {
//                msgTo.setFromJid(msgFrom.getFromJid());
//            }
//            if (msgFrom.getUserType() != null) {
//                msgTo.setUserType(msgFrom.getUserType());
//            }
//            if (msgFrom.getToJid() != null) {
//                msgTo.setToJid(msgFrom.getToJid());
//            }
//            if (msgFrom.getFriendName() != null) {
//                msgTo.setFriendName(msgFrom.getFriendName());
//            }
//            if (msgFrom.getImageFullUrl() != null) {
//                msgTo.setImageFullUrl(msgFrom.getImageFullUrl());
//            }
//            if (msgFrom.getImageLocalUrl() != null) {
//                msgTo.setImageLocalUrl(msgFrom.getImageLocalUrl());
//            }
//            if (msgFrom.getImageThumbUrl() != null) {
//                msgTo.setImageThumbUrl(msgFrom.getImageThumbUrl());
//            }
//            if (msgFrom.getMediaType() != null) {
//                msgTo.setMediaType(msgFrom.getMediaType());
//            }
//            if (msgFrom.getMsgDate() != null) {
//                msgTo.setMsgDate(msgFrom.getMsgDate());
//            }
//            if (msgFrom.getMsgStatus() != AppConstants.MsgStatus.unsent.getValue()) {
//                msgTo.setMsgStatus(msgFrom.getMsgStatus());
//            }
////            if (msgFrom.getMsgId() != null) {
////                msgTo.setMsgId(msgFrom.getMsgId());
////            }
//            if (msgFrom.getMsgText() != null) {
//                msgTo.setMsgText(msgFrom.getMsgText());
//            }
//            if (msgFrom.getMsgTimestamp() != 0) {
//                msgTo.setMsgTimestamp(msgFrom.getMsgTimestamp());
//            }
//            if (msgFrom.getMsgTime() != null) {
//                msgTo.setMsgTime(msgFrom.getMsgTime());
//            }
//            if (msgFrom.getThreadId() != null) {
//                msgTo.setThreadId(msgFrom.getThreadId());
//            }
//            if (msgFrom.getMsgCreatorJid() != null) {
//                msgTo.setMsgCreatorJid(msgFrom.getMsgCreatorJid());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            if (startTransaction && realm.isInTransaction()) realm.commitTransaction();
//        }
//
//
//    }
//
////    public void copyChatUser(Realm realm, ChatUser chatUserCopyTo, ChatUser chatUserCopyFrom, boolean startTransaction) {
////        if (chatUserCopyFrom == null || chatUserCopyTo == null) return;
////        if (startTransaction && !realm.isInTransaction()) realm.beginTransaction();
////        try {
////            if (!StringUtils.isNullOrEmpty(chatUserCopyFrom.getName())) {
////                chatUserCopyTo.setName(chatUserCopyFrom.getName());
////            }
////            if (!StringUtils.isNullOrEmpty(chatUserCopyFrom.getMobile())) {
////                chatUserCopyTo.setMobile(chatUserCopyFrom.getMobile());
////            }
////            if (!StringUtils.isNullOrEmpty(chatUserCopyFrom.getProfilePic())) {
////                chatUserCopyTo.setProfilePic(chatUserCopyFrom.getProfilePic());
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        } finally {
////            if (startTransaction && !realm.isInTransaction()) realm.commitTransaction();
////        }
////
////    }
//
////    public boolean saveChatUser(String jabberId, ChatUser chatUser, boolean startTransaction) {
////
////        if (chatUser == null) return false;
////        if (startTransaction && !realm.isInTransaction())
////            realm.beginTransaction();
////        try {
////            if (checkIfChatThreadExists(jabberId)) {
////                //Add value to the thread
////                ChatThreads chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
////                ChatUser oldChatuser = chatThreads.getChatUser();
////                if (oldChatuser != null) {
////                    copyChatUser(oldChatuser, chatUser, false);
////                } else {
////                    ChatUser chatUserNew = realm.createObject(ChatUser.class);
////                    copyChatUser(chatUserNew, chatUser, false);
////                    chatThreads.setChatUser(chatUserNew);
////                }
////
////            }
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        } finally {
////            if (startTransaction && !realm.isInTransaction()) {
////                realm.commitTransaction();
////            }
////        }
////        return true;
////    }
//
////    public ChatUser getChatUserDetails(String jabberId) {
////        ChatThreads chatThreads = null;
////        try {
////            chatThreads = realm.where(ChatThreads.class).equalTo("jabberId", jabberId).findFirst();
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
////        if (chatThreads == null) return null;
////        return chatThreads.getChatUser();
////    }
//
//
    public void deleteDb() {
        try {
            if (!realm.isInTransaction())
                realm.beginTransaction();
            realm.delete(ContestLive.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            realm.commitTransaction();
        }
    }

}

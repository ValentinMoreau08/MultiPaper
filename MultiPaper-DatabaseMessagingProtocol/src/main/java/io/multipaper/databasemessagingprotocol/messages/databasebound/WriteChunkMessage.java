package io.multipaper.databasemessagingprotocol.messages.databasebound;

import io.multipaper.databasemessagingprotocol.ExtendedByteBuf;

public class WriteChunkMessage extends DatabaseBoundMessage {

    public final String path;
    public final int cx;
    public final int cz;
    public final byte[] data;
    public final boolean isTransientEntities;

    public WriteChunkMessage(String path, int cx, int cz, byte[] data) {
        this(path, cx, cz, data, false);
    }

    public WriteChunkMessage(String path, int cx, int cz, byte[] data, boolean isTransientEntities) {
        this.path = path;
        this.cx = cx;
        this.cz = cz;
        this.data = data;
        this.isTransientEntities = isTransientEntities;
    }

    public WriteChunkMessage(ExtendedByteBuf byteBuf) {
        path = byteBuf.readString();
        cx = byteBuf.readInt();
        cz = byteBuf.readInt();
        data = new byte[byteBuf.readVarInt()];
        byteBuf.readBytes(data);
        isTransientEntities = byteBuf.readBoolean();
    }

    @Override
    public void write(ExtendedByteBuf byteBuf) {
        byteBuf.writeString(path);
        byteBuf.writeInt(cx);
        byteBuf.writeInt(cz);
        byteBuf.writeVarInt(data.length);
        byteBuf.writeBytes(data);
        byteBuf.writeBoolean(isTransientEntities);
    }

    @Override
    public void handle(DatabaseBoundMessageHandler handler) {
        handler.handle(this);
    }
}

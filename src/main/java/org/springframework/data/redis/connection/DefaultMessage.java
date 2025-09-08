/*
 * Copyright 2011-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.connection;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Default message implementation.
 * <p>
 * 目前看来，唯一的 Message 实现类。
 *
 * @author Costin Leau
 * @author Christoph Strobl
 * @author Mark Paluch
 */
public class DefaultMessage implements Message {

    private static final byte[] EMPTY = new byte[0];

    // 表示消息是从哪个 Redis Channel 发布/订阅得到的
    // 立即为 Pub/Sub 机制 主体名称，这里为了效率，直接使用了 byte[]
    private final byte[] channel;

    // 真正承载业务数据的消息内容 payload
    // Redis Pub/Sub 允许发布任何二进制内容，所以用 byte[]
    private final byte[] body;
    private @Nullable String toString;

    public DefaultMessage(byte[] channel, byte[] body) {

        Assert.notNull(channel, "Channel must not be null!");
        Assert.notNull(body, "Body must not be null!");

        this.body = body;
        this.channel = channel;
    }

    /**
     * 始终不会返回给你底层的字节数组，而是给你一个拷贝对象。
     */
    @Override
    public byte[] getChannel() {
        return channel.length == 0 ? EMPTY : channel.clone();
    }

    /**
     * 始终不会返回给你底层的字节数组，而是给你一个拷贝对象。
     */
    @Override
    public byte[] getBody() {
        return body.length == 0 ? EMPTY : body.clone();
    }

    @Override
    public String toString() {

        if (toString == null) {
            toString = new String(body);
        }
        return toString;
    }
}

private class mixed1 {
// https://raw.githubusercontent.com/spring-projects/spring-kafka/main/spring-kafka/src/main/java/org/springframework/kafka/core/DefaultKafkaConsumerFactory.java
private Consumer<K, V> createConsumerWithAdjustedProperties(@Nullable String groupId,String clientIdPrefix,
@Nullable Properties properties,boolean overrideClientIdPrefix,String clientIdSuffix,
        boolean shouldModifyClientId){

        Map<String, Object> modifiedConfigs=new HashMap<>(this.configs);
        if(groupId!=null){
        modifiedConfigs.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        }
        if(shouldModifyClientId){
        modifiedConfigs.put(ConsumerConfig.CLIENT_ID_CONFIG,
        (overrideClientIdPrefix?clientIdPrefix
        :modifiedConfigs.get(ConsumerConfig.CLIENT_ID_CONFIG))+clientIdSuffix);
        }
        if(properties!=null){
        Set<String> stringPropertyNames=properties.stringPropertyNames();  // to get any nested default Properties
        stringPropertyNames
        .stream()
        .filter(name->!name.equals(ConsumerConfig.CLIENT_ID_CONFIG)
        &&!name.equals(ConsumerConfig.GROUP_ID_CONFIG))
        .forEach(name->modifiedConfigs.put(name,properties.getProperty(name)));
        properties.entrySet().stream()
        .filter(entry->!entry.getKey().equals(ConsumerConfig.CLIENT_ID_CONFIG)
        &&!entry.getKey().equals(ConsumerConfig.GROUP_ID_CONFIG)
        &&!stringPropertyNames.contains(entry.getKey())
        &&entry.getKey()instanceof String)
        .forEach(entry->modifiedConfigs.put((String)entry.getKey(),entry.getValue()));
        checkInaccessible(properties,modifiedConfigs);
        }
        return createKafkaConsumer(modifiedConfigs);
        }

// https://github.com/spring-projects/spring-authorization-server/blob/0.1.2/oauth2-authorization-server/src/main/java/org/springframework/security/oauth2/core/OAuth2TokenIntrospection.java
private void validate(){
        Assert.notNull(this.claims.get(OAuth2TokenIntrospectionClaimNames.ACTIVE),"active cannot be null");
        Assert.isInstanceOf(Boolean.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.ACTIVE),"active must be of type boolean");
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.SCOPE)){
        Assert.isInstanceOf(List.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.SCOPE),"scope must be of type List");
        }
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.EXP)){
        Assert.isInstanceOf(Instant.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.EXP),"exp must be of type Instant");
        }
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.IAT)){
        Assert.isInstanceOf(Instant.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.IAT),"iat must be of type Instant");
        }
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.NBF)){
        Assert.isInstanceOf(Instant.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.NBF),"nbf must be of type Instant");
        }
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.AUD)){
        Assert.isInstanceOf(List.class,this.claims.get(OAuth2TokenIntrospectionClaimNames.AUD),"aud must be of type List");
        }
        if(this.claims.containsKey(OAuth2TokenIntrospectionClaimNames.ISS)){
        validateURL(this.claims.get(OAuth2TokenIntrospectionClaimNames.ISS),"iss must be a valid URL");
        }
        }

//https://github.com/spring-projects/spring-security/blob/5.5.6/crypto/src/main/java/org/springframework/security/crypto/codec/Base64.java
    private static int decode4to3(final byte[] source, final int srcOffset, final byte[] destination,
                                  final int destOffset, final int options) {

        // Lots of error checking and exception throwing
        if (source == null) {
            throw new NullPointerException("Source array was null.");
        } // end if
        if (destination == null) {
            throw new NullPointerException("Destination array was null.");
        } // end if
        if (srcOffset < 0 || srcOffset + 3 >= source.length) {
            throw new IllegalArgumentException(
                    String.format("Source array with length %d cannot have offset of %d and still process four bytes.",
                            source.length, srcOffset));
        } // end if
        if (destOffset < 0 || destOffset + 2 >= destination.length) {
            throw new IllegalArgumentException(String.format(
                    "Destination array with length %d cannot have offset of %d and still store three bytes.",
                    destination.length, destOffset));
        } // end if

        byte[] DECODABET = getDecodabet(options);

        // Example: Dk==
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            // Two ways to do the same thing. Don't know which way I like best.
            // int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6 )
            // | ( ( DECODABET[ source[ srcOffset + 1] ] << 24 ) >>> 12 );
            int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
                    | ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12);

            destination[destOffset] = (byte) (outBuff >>> 16);
            return 1;
        }

        // Example: DkL=
        else if (source[srcOffset + 3] == EQUALS_SIGN) {
            // Two ways to do the same thing. Don't know which way I like best.
            // int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6 )
            // | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
            // | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 );
            int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
                    | ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
                    | ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6);

            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        }

        // Example: DkLE
        else {
            // Two ways to do the same thing. Don't know which way I like best.
            // int outBuff = ( ( DECODABET[ source[ srcOffset ] ] << 24 ) >>> 6 )
            // | ( ( DECODABET[ source[ srcOffset + 1 ] ] << 24 ) >>> 12 )
            // | ( ( DECODABET[ source[ srcOffset + 2 ] ] << 24 ) >>> 18 )
            // | ( ( DECODABET[ source[ srcOffset + 3 ] ] << 24 ) >>> 24 );
            int outBuff = ((DECODABET[source[srcOffset]] & 0xFF) << 18)
                    | ((DECODABET[source[srcOffset + 1]] & 0xFF) << 12)
                    | ((DECODABET[source[srcOffset + 2]] & 0xFF) << 6) | ((DECODABET[source[srcOffset + 3]] & 0xFF));

            destination[destOffset] = (byte) (outBuff >> 16);
            destination[destOffset + 1] = (byte) (outBuff >> 8);
            destination[destOffset + 2] = (byte) (outBuff);

            return 3;
        }
    }

    //https://github.com/spring-projects/spring-boot-data-geode/blob/1.6.0/spring-geode/src/main/java/org/springframework/geode/core/util/ObjectUtils.java
    public static <T> T invoke(Object obj, String methodName) {

        return (T) Optional.ofNullable(obj)
                .map(Object::getClass)
                .map(type -> ReflectionUtils.findMethod(type, methodName))
                .map(ObjectUtils::makeAccessible)
                .map(method -> ReflectionUtils.invokeMethod(method, obj))
                .orElseThrow(() -> newIllegalArgumentException("Method [%1$s] on Object of type [%2$s] not found",
                        methodName, org.springframework.util.ObjectUtils.nullSafeClassName(obj)));
    }

}


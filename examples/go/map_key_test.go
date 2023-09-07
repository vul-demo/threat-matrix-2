/*
*    ------ BEGIN LICENSE ATTRIBUTION ------
*    
*    Portions of this file have been appropriated or derived from the following project(s) and therefore require attribution to the original licenses and authors.
*    
*    Project: http://jsoniter.com/migrate-from-go-std.html
*    Release: https://github.com/json-iterator/go/releases/tag/v1.1.12
*    Source File: map_key_test.go
*    
*    Copyrights:
*      copyright (c) 2016 json-iterator
*    
*    Licenses:
*      MIT License
*      SPDXId: MIT
*    
*    Auto-attribution by Threatrix, Inc.
*    
*    ------ END LICENSE ATTRIBUTION ------
*/
// +build go1.15
// remove these tests temporarily until https://github.com/golang/go/issues/38105 and
// https://github.com/golang/go/issues/38940 is fixed

package test

import (
	"encoding"
	"strings"
)

func init() {
	testCases = append(testCases,
		(*map[stringKeyType]string)(nil),
		(*map[structKeyType]string)(nil),
	)
}

type stringKeyType string

func (k stringKeyType) MarshalText() ([]byte, error) {
	return []byte("MANUAL__" + k), nil
}

func (k *stringKeyType) UnmarshalText(text []byte) error {
	*k = stringKeyType(strings.TrimPrefix(string(text), "MANUAL__"))
	return nil
}

var _ encoding.TextMarshaler = stringKeyType("")
var _ encoding.TextUnmarshaler = new(stringKeyType)

type structKeyType struct {
	X string
}

func (k structKeyType) MarshalText() ([]byte, error) {
	return []byte("MANUAL__" + k.X), nil
}

func (k *structKeyType) UnmarshalText(text []byte) error {
	k.X = strings.TrimPrefix(string(text), "MANUAL__")
	return nil
}

var _ encoding.TextMarshaler = structKeyType{}
var _ encoding.TextUnmarshaler = &structKeyType{}

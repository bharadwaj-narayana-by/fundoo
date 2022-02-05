package org.by.bharadwaj.fundoo.response;

import org.by.bharadwaj.fundoo.exception.FundooException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

	private int statusCode;
	private String statusMessage;
	private Object data;
}

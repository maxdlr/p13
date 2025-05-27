package com.maxdlr.p13.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

  private static final Logger logger = LoggerFactory.getLogger(GraphQLExceptionResolver.class);

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    logger.debug("Resolving GraphQL exception: {}", ex.getMessage(), ex);

    return switch (ex) {
      case MessageUserNotFoundException e -> createNotFoundError(e, env);
      case ConversationUserNotFoundException e -> createNotFoundError(e, env);
      case MessageNotFoundException e -> createNotFoundError(e, env);
      case ConversationNotFoundException e -> createNotFoundError(e, env);
      default -> {
        logger.error("Unexpected error in GraphQL resolver", ex);
        yield createInternalError(ex, env);
      }
    };
  }
  // protected GraphQLError resolveToSingleError(Throwable ex,
  // DataFetchingEnvironment env) {
  // if (ex instanceof VehicleNotFoundException) {
  // return GraphqlErrorBuilder.newError()
  // .errorType(ErrorType.NOT_FOUND)
  // .message(ex.getMessage())
  // .path(env.getExecutionStepInfo().getPath())
  // .location(env.getField().getSourceLocation())
  // .build();
  // } else {
  // return null;
  // }
  // }

  private GraphQLError createNotFoundError(Exception ex, DataFetchingEnvironment env) {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("exceptionType", ex.getClass().getSimpleName());
    extensions.put("timestamp", System.currentTimeMillis());
    return GraphqlErrorBuilder.newError()
        .message(ex.getMessage())
        .errorType(ErrorType.NOT_FOUND)
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .extensions(extensions)
        .build();
  }

  private GraphQLError createInternalError(Throwable ex, DataFetchingEnvironment env) {
    Map<String, Object> extensions = new HashMap<>();
    extensions.put("originalMessage", ex.getMessage());
    extensions.put("timestamp", System.currentTimeMillis());
    return GraphqlErrorBuilder.newError()
        .message("An internal error occurred")
        .errorType(ErrorType.INTERNAL_ERROR)
        .path(env.getExecutionStepInfo().getPath())
        .location(env.getField().getSourceLocation())
        .extensions(extensions)
        .build();
  }
}
